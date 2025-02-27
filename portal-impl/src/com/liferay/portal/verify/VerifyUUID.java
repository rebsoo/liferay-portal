/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.verify;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.BaseUpgradeCallable;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyUUID extends VerifyProcess {

	public static void verify(VerifiableUUIDModel... verifiableUUIDModels)
		throws Exception {

		VerifyUUID verifyUUID = new VerifyUUID();

		verifyUUID.doVerify(verifiableUUIDModels);
	}

	@Override
	protected void doVerify() throws Exception {
		Map<String, VerifiableUUIDModel> verifiableUUIDModelsMap =
			PortalBeanLocatorUtil.locate(VerifiableUUIDModel.class);

		Collection<VerifiableUUIDModel> verifiableUUIDModels =
			verifiableUUIDModelsMap.values();

		doVerify(verifiableUUIDModels.toArray(new VerifiableUUIDModel[0]));
	}

	protected void doVerify(VerifiableUUIDModel... verifiableUUIDModels)
		throws Exception {

		List<VerifyUUIDUpgradeCallable> verifyUUIDUpgradeCallables =
			new ArrayList<>(verifiableUUIDModels.length);

		for (VerifiableUUIDModel verifiableUUIDModel : verifiableUUIDModels) {
			VerifyUUIDUpgradeCallable verifyUUIDUpgradeCallable =
				new VerifyUUIDUpgradeCallable(verifiableUUIDModel);

			verifyUUIDUpgradeCallables.add(verifyUUIDUpgradeCallable);
		}

		doVerify(verifyUUIDUpgradeCallables);
	}

	protected void verifyUUID(VerifiableUUIDModel verifiableUUIDModel)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		if (db.isSupportsNewUuidFunction()) {
			try (LoggingTimer loggingTimer = new LoggingTimer(
					verifiableUUIDModel.getTableName());
				Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"update ", verifiableUUIDModel.getTableName(),
							" set uuid_ = ", db.getNewUuidFunctionName(),
							" where uuid_ is null or uuid_ = ''"))) {

				preparedStatement.executeUpdate();

				return;
			}
		}

		StringBundler sb = new StringBundler(5);

		sb.append("update ");
		sb.append(verifiableUUIDModel.getTableName());
		sb.append(" set uuid_ = ? where ");
		sb.append(verifiableUUIDModel.getPrimaryKeyColumnName());
		sb.append(" = ?");

		try (LoggingTimer loggingTimer = new LoggingTimer(
				verifiableUUIDModel.getTableName());
			Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ", verifiableUUIDModel.getPrimaryKeyColumnName(),
					" from ", verifiableUUIDModel.getTableName(),
					" where uuid_ is null or uuid_ = ''"));
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(sb.toString()))) {

			while (resultSet.next()) {
				long pk = resultSet.getLong(
					verifiableUUIDModel.getPrimaryKeyColumnName());

				preparedStatement2.setString(1, PortalUUIDUtil.generate());
				preparedStatement2.setLong(2, pk);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private class VerifyUUIDUpgradeCallable extends BaseUpgradeCallable<Void> {

		@Override
		protected Void doCall() throws Exception {
			verifyUUID(_verifiableUUIDModel);

			return null;
		}

		private VerifyUUIDUpgradeCallable(
			VerifiableUUIDModel verifiableUUIDModel) {

			_verifiableUUIDModel = verifiableUUIDModel;
		}

		private final VerifiableUUIDModel _verifiableUUIDModel;

	}

}