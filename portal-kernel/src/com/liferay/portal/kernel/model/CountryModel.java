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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.bean.AutoEscape;

import java.util.Date;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the Country service. Represents a row in the &quot;Country&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.model.impl.CountryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.model.impl.CountryImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Country
 * @generated
 */
@ProviderType
public interface CountryModel
	extends BaseModel<Country>, MVCCModel, ShardedModel, StagedAuditedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a country model instance should use the {@link Country} interface instead.
	 */

	/**
	 * Returns the primary key of this country.
	 *
	 * @return the primary key of this country
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this country.
	 *
	 * @param primaryKey the primary key of this country
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this country.
	 *
	 * @return the mvcc version of this country
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this country.
	 *
	 * @param mvccVersion the mvcc version of this country
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this country.
	 *
	 * @return the uuid of this country
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this country.
	 *
	 * @param uuid the uuid of this country
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the default language ID of this country.
	 *
	 * @return the default language ID of this country
	 */
	@AutoEscape
	public String getDefaultLanguageId();

	/**
	 * Sets the default language ID of this country.
	 *
	 * @param defaultLanguageId the default language ID of this country
	 */
	public void setDefaultLanguageId(String defaultLanguageId);

	/**
	 * Returns the country ID of this country.
	 *
	 * @return the country ID of this country
	 */
	public long getCountryId();

	/**
	 * Sets the country ID of this country.
	 *
	 * @param countryId the country ID of this country
	 */
	public void setCountryId(long countryId);

	/**
	 * Returns the company ID of this country.
	 *
	 * @return the company ID of this country
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this country.
	 *
	 * @param companyId the company ID of this country
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this country.
	 *
	 * @return the user ID of this country
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this country.
	 *
	 * @param userId the user ID of this country
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this country.
	 *
	 * @return the user uuid of this country
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this country.
	 *
	 * @param userUuid the user uuid of this country
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this country.
	 *
	 * @return the user name of this country
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this country.
	 *
	 * @param userName the user name of this country
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this country.
	 *
	 * @return the create date of this country
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this country.
	 *
	 * @param createDate the create date of this country
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this country.
	 *
	 * @return the modified date of this country
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this country.
	 *
	 * @param modifiedDate the modified date of this country
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the a2 of this country.
	 *
	 * @return the a2 of this country
	 */
	@AutoEscape
	public String getA2();

	/**
	 * Sets the a2 of this country.
	 *
	 * @param a2 the a2 of this country
	 */
	public void setA2(String a2);

	/**
	 * Returns the a3 of this country.
	 *
	 * @return the a3 of this country
	 */
	@AutoEscape
	public String getA3();

	/**
	 * Sets the a3 of this country.
	 *
	 * @param a3 the a3 of this country
	 */
	public void setA3(String a3);

	/**
	 * Returns the active of this country.
	 *
	 * @return the active of this country
	 */
	public boolean getActive();

	/**
	 * Returns <code>true</code> if this country is active.
	 *
	 * @return <code>true</code> if this country is active; <code>false</code> otherwise
	 */
	public boolean isActive();

	/**
	 * Sets whether this country is active.
	 *
	 * @param active the active of this country
	 */
	public void setActive(boolean active);

	/**
	 * Returns the billing allowed of this country.
	 *
	 * @return the billing allowed of this country
	 */
	public boolean getBillingAllowed();

	/**
	 * Returns <code>true</code> if this country is billing allowed.
	 *
	 * @return <code>true</code> if this country is billing allowed; <code>false</code> otherwise
	 */
	public boolean isBillingAllowed();

	/**
	 * Sets whether this country is billing allowed.
	 *
	 * @param billingAllowed the billing allowed of this country
	 */
	public void setBillingAllowed(boolean billingAllowed);

	/**
	 * Returns the group filter enabled of this country.
	 *
	 * @return the group filter enabled of this country
	 */
	public boolean getGroupFilterEnabled();

	/**
	 * Returns <code>true</code> if this country is group filter enabled.
	 *
	 * @return <code>true</code> if this country is group filter enabled; <code>false</code> otherwise
	 */
	public boolean isGroupFilterEnabled();

	/**
	 * Sets whether this country is group filter enabled.
	 *
	 * @param groupFilterEnabled the group filter enabled of this country
	 */
	public void setGroupFilterEnabled(boolean groupFilterEnabled);

	/**
	 * Returns the idd of this country.
	 *
	 * @return the idd of this country
	 */
	@AutoEscape
	public String getIdd();

	/**
	 * Sets the idd of this country.
	 *
	 * @param idd the idd of this country
	 */
	public void setIdd(String idd);

	/**
	 * Returns the name of this country.
	 *
	 * @return the name of this country
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this country.
	 *
	 * @param name the name of this country
	 */
	public void setName(String name);

	/**
	 * Returns the number of this country.
	 *
	 * @return the number of this country
	 */
	@AutoEscape
	public String getNumber();

	/**
	 * Sets the number of this country.
	 *
	 * @param number the number of this country
	 */
	public void setNumber(String number);

	/**
	 * Returns the position of this country.
	 *
	 * @return the position of this country
	 */
	public double getPosition();

	/**
	 * Sets the position of this country.
	 *
	 * @param position the position of this country
	 */
	public void setPosition(double position);

	/**
	 * Returns the shipping allowed of this country.
	 *
	 * @return the shipping allowed of this country
	 */
	public boolean getShippingAllowed();

	/**
	 * Returns <code>true</code> if this country is shipping allowed.
	 *
	 * @return <code>true</code> if this country is shipping allowed; <code>false</code> otherwise
	 */
	public boolean isShippingAllowed();

	/**
	 * Sets whether this country is shipping allowed.
	 *
	 * @param shippingAllowed the shipping allowed of this country
	 */
	public void setShippingAllowed(boolean shippingAllowed);

	/**
	 * Returns the subject to vat of this country.
	 *
	 * @return the subject to vat of this country
	 */
	public boolean getSubjectToVAT();

	/**
	 * Returns <code>true</code> if this country is subject to vat.
	 *
	 * @return <code>true</code> if this country is subject to vat; <code>false</code> otherwise
	 */
	public boolean isSubjectToVAT();

	/**
	 * Sets whether this country is subject to vat.
	 *
	 * @param subjectToVAT the subject to vat of this country
	 */
	public void setSubjectToVAT(boolean subjectToVAT);

	/**
	 * Returns the zip required of this country.
	 *
	 * @return the zip required of this country
	 */
	public boolean getZipRequired();

	/**
	 * Returns <code>true</code> if this country is zip required.
	 *
	 * @return <code>true</code> if this country is zip required; <code>false</code> otherwise
	 */
	public boolean isZipRequired();

	/**
	 * Sets whether this country is zip required.
	 *
	 * @param zipRequired the zip required of this country
	 */
	public void setZipRequired(boolean zipRequired);

	/**
	 * Returns the last publish date of this country.
	 *
	 * @return the last publish date of this country
	 */
	public Date getLastPublishDate();

	/**
	 * Sets the last publish date of this country.
	 *
	 * @param lastPublishDate the last publish date of this country
	 */
	public void setLastPublishDate(Date lastPublishDate);

	public String[] getAvailableLanguageIds();

	public String getTitle();

	public String getTitle(String languageId);

	public String getTitle(String languageId, boolean useDefault);

	public String getTitleMapAsXML();

	public Map<String, String> getLanguageIdToTitleMap();

}