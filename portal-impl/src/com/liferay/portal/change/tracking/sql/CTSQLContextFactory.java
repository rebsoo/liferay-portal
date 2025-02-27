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

package com.liferay.portal.change.tracking.sql;

import java.util.List;

/**
 * @author Preston Crary
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public interface CTSQLContextFactory {

	public CTSQLContext createCTSQLContext(
		long ctCollectionId, String tableName, String primaryColumnName,
		Class<?> clazz);

	public interface CTSQLContext {

		public List<Long> getExcludePKs();

		public boolean hasAdded();

		public boolean hasModified();

	}

}