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

package com.liferay.commerce.price.list.service.persistence.impl;

import com.liferay.commerce.price.list.exception.NoSuchPriceListDiscountRelException;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRelTable;
import com.liferay.commerce.price.list.model.impl.CommercePriceListDiscountRelImpl;
import com.liferay.commerce.price.list.model.impl.CommercePriceListDiscountRelModelImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListDiscountRelPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce price list discount rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListDiscountRelPersistenceImpl
	extends BasePersistenceImpl<CommercePriceListDiscountRel>
	implements CommercePriceListDiscountRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePriceListDiscountRelUtil</code> to access the commerce price list discount rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePriceListDiscountRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the commerce price list discount rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list discount rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @return the range of matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list discount rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list discount rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CommercePriceListDiscountRel> list = null;

		if (useFinderCache) {
			list = (List<CommercePriceListDiscountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListDiscountRel commercePriceListDiscountRel :
						list) {

					if (!uuid.equals(commercePriceListDiscountRel.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceListDiscountRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<CommercePriceListDiscountRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (commercePriceListDiscountRel != null) {
			return commercePriceListDiscountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListDiscountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		List<CommercePriceListDiscountRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commercePriceListDiscountRel != null) {
			return commercePriceListDiscountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListDiscountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListDiscountRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list discount rels before and after the current commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the current commerce price list discount rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListDiscountRel[] findByUuid_PrevAndNext(
			long commercePriceListDiscountRelId, String uuid,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			findByPrimaryKey(commercePriceListDiscountRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListDiscountRel[] array =
				new CommercePriceListDiscountRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commercePriceListDiscountRel, uuid, orderByComparator,
				true);

			array[1] = commercePriceListDiscountRel;

			array[2] = getByUuid_PrevAndNext(
				session, commercePriceListDiscountRel, uuid, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceListDiscountRel getByUuid_PrevAndNext(
		Session session,
		CommercePriceListDiscountRel commercePriceListDiscountRel, String uuid,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommercePriceListDiscountRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceListDiscountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListDiscountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list discount rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommercePriceListDiscountRel commercePriceListDiscountRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commercePriceListDiscountRel);
		}
	}

	/**
	 * Returns the number of commerce price list discount rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list discount rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"commercePriceListDiscountRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commercePriceListDiscountRel.uuid IS NULL OR commercePriceListDiscountRel.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @return the range of matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CommercePriceListDiscountRel> list = null;

		if (useFinderCache) {
			list = (List<CommercePriceListDiscountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListDiscountRel commercePriceListDiscountRel :
						list) {

					if (!uuid.equals(commercePriceListDiscountRel.getUuid()) ||
						(companyId !=
							commercePriceListDiscountRel.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceListDiscountRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<CommercePriceListDiscountRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commercePriceListDiscountRel != null) {
			return commercePriceListDiscountRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListDiscountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		List<CommercePriceListDiscountRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commercePriceListDiscountRel != null) {
			return commercePriceListDiscountRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListDiscountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListDiscountRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list discount rels before and after the current commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the current commerce price list discount rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListDiscountRel[] findByUuid_C_PrevAndNext(
			long commercePriceListDiscountRelId, String uuid, long companyId,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			findByPrimaryKey(commercePriceListDiscountRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListDiscountRel[] array =
				new CommercePriceListDiscountRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commercePriceListDiscountRel, uuid, companyId,
				orderByComparator, true);

			array[1] = commercePriceListDiscountRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, commercePriceListDiscountRel, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceListDiscountRel getByUuid_C_PrevAndNext(
		Session session,
		CommercePriceListDiscountRel commercePriceListDiscountRel, String uuid,
		long companyId,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommercePriceListDiscountRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceListDiscountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListDiscountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list discount rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommercePriceListDiscountRel commercePriceListDiscountRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceListDiscountRel);
		}
	}

	/**
	 * Returns the number of commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list discount rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"commercePriceListDiscountRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commercePriceListDiscountRel.uuid IS NULL OR commercePriceListDiscountRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commercePriceListDiscountRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommercePriceListId;
	private FinderPath _finderPathWithoutPaginationFindByCommercePriceListId;
	private FinderPath _finderPathCountByCommercePriceListId;

	/**
	 * Returns all the commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByCommercePriceListId(
		long commercePriceListId) {

		return findByCommercePriceListId(
			commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @return the range of matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {

		return findByCommercePriceListId(commercePriceListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		return findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommercePriceListId;
				finderArgs = new Object[] {commercePriceListId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommercePriceListId;
			finderArgs = new Object[] {
				commercePriceListId, start, end, orderByComparator
			};
		}

		List<CommercePriceListDiscountRel> list = null;

		if (useFinderCache) {
			list = (List<CommercePriceListDiscountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListDiscountRel commercePriceListDiscountRel :
						list) {

					if (commercePriceListId !=
							commercePriceListDiscountRel.
								getCommercePriceListId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceListDiscountRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				list = (List<CommercePriceListDiscountRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel findByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			fetchByCommercePriceListId_First(
				commercePriceListId, orderByComparator);

		if (commercePriceListDiscountRel != null) {
			return commercePriceListDiscountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListDiscountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		List<CommercePriceListDiscountRel> list = findByCommercePriceListId(
			commercePriceListId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel findByCommercePriceListId_Last(
			long commercePriceListId,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			fetchByCommercePriceListId_Last(
				commercePriceListId, orderByComparator);

		if (commercePriceListDiscountRel != null) {
			return commercePriceListDiscountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListDiscountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		int count = countByCommercePriceListId(commercePriceListId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListDiscountRel> list = findByCommercePriceListId(
			commercePriceListId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list discount rels before and after the current commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the current commerce price list discount rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListDiscountRel[] findByCommercePriceListId_PrevAndNext(
			long commercePriceListDiscountRelId, long commercePriceListId,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			findByPrimaryKey(commercePriceListDiscountRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListDiscountRel[] array =
				new CommercePriceListDiscountRelImpl[3];

			array[0] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListDiscountRel, commercePriceListId,
				orderByComparator, true);

			array[1] = commercePriceListDiscountRel;

			array[2] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListDiscountRel, commercePriceListId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceListDiscountRel getByCommercePriceListId_PrevAndNext(
		Session session,
		CommercePriceListDiscountRel commercePriceListDiscountRel,
		long commercePriceListId,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommercePriceListDiscountRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceListId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceListDiscountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListDiscountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list discount rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	@Override
	public void removeByCommercePriceListId(long commercePriceListId) {
		for (CommercePriceListDiscountRel commercePriceListDiscountRel :
				findByCommercePriceListId(
					commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceListDiscountRel);
		}
	}

	/**
	 * Returns the number of commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list discount rels
	 */
	@Override
	public int countByCommercePriceListId(long commercePriceListId) {
		FinderPath finderPath = _finderPathCountByCommercePriceListId;

		Object[] finderArgs = new Object[] {commercePriceListId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2 =
			"commercePriceListDiscountRel.commercePriceListId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the commerce price list discount rel where commerceDiscountId = &#63; and commercePriceListId = &#63; or throws a <code>NoSuchPriceListDiscountRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel findByC_C(
			long commerceDiscountId, long commercePriceListId)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel = fetchByC_C(
			commerceDiscountId, commercePriceListId);

		if (commercePriceListDiscountRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceDiscountId=");
			sb.append(commerceDiscountId);

			sb.append(", commercePriceListId=");
			sb.append(commercePriceListId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPriceListDiscountRelException(sb.toString());
		}

		return commercePriceListDiscountRel;
	}

	/**
	 * Returns the commerce price list discount rel where commerceDiscountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel fetchByC_C(
		long commerceDiscountId, long commercePriceListId) {

		return fetchByC_C(commerceDiscountId, commercePriceListId, true);
	}

	/**
	 * Returns the commerce price list discount rel where commerceDiscountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	@Override
	public CommercePriceListDiscountRel fetchByC_C(
		long commerceDiscountId, long commercePriceListId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {commerceDiscountId, commercePriceListId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C, finderArgs);
		}

		if (result instanceof CommercePriceListDiscountRel) {
			CommercePriceListDiscountRel commercePriceListDiscountRel =
				(CommercePriceListDiscountRel)result;

			if ((commerceDiscountId !=
					commercePriceListDiscountRel.getCommerceDiscountId()) ||
				(commercePriceListId !=
					commercePriceListDiscountRel.getCommercePriceListId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEDISCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				queryPos.add(commercePriceListId);

				List<CommercePriceListDiscountRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					CommercePriceListDiscountRel commercePriceListDiscountRel =
						list.get(0);

					result = commercePriceListDiscountRel;

					cacheResult(commercePriceListDiscountRel);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CommercePriceListDiscountRel)result;
		}
	}

	/**
	 * Removes the commerce price list discount rel where commerceDiscountId = &#63; and commercePriceListId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the commerce price list discount rel that was removed
	 */
	@Override
	public CommercePriceListDiscountRel removeByC_C(
			long commerceDiscountId, long commercePriceListId)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel = findByC_C(
			commerceDiscountId, commercePriceListId);

		return remove(commercePriceListDiscountRel);
	}

	/**
	 * Returns the number of commerce price list discount rels where commerceDiscountId = &#63; and commercePriceListId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list discount rels
	 */
	@Override
	public int countByC_C(long commerceDiscountId, long commercePriceListId) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {
			commerceDiscountId, commercePriceListId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTDISCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEDISCOUNTID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				queryPos.add(commercePriceListId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_COMMERCEDISCOUNTID_2 =
		"commercePriceListDiscountRel.commerceDiscountId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2 =
		"commercePriceListDiscountRel.commercePriceListId = ?";

	public CommercePriceListDiscountRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommercePriceListDiscountRel.class);

		setModelImplClass(CommercePriceListDiscountRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePriceListDiscountRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce price list discount rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListDiscountRel the commerce price list discount rel
	 */
	@Override
	public void cacheResult(
		CommercePriceListDiscountRel commercePriceListDiscountRel) {

		entityCache.putResult(
			CommercePriceListDiscountRelImpl.class,
			commercePriceListDiscountRel.getPrimaryKey(),
			commercePriceListDiscountRel);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				commercePriceListDiscountRel.getCommerceDiscountId(),
				commercePriceListDiscountRel.getCommercePriceListId()
			},
			commercePriceListDiscountRel);
	}

	/**
	 * Caches the commerce price list discount rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListDiscountRels the commerce price list discount rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceListDiscountRel> commercePriceListDiscountRels) {

		for (CommercePriceListDiscountRel commercePriceListDiscountRel :
				commercePriceListDiscountRels) {

			if (entityCache.getResult(
					CommercePriceListDiscountRelImpl.class,
					commercePriceListDiscountRel.getPrimaryKey()) == null) {

				cacheResult(commercePriceListDiscountRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce price list discount rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePriceListDiscountRelImpl.class);

		finderCache.clearCache(CommercePriceListDiscountRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce price list discount rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommercePriceListDiscountRel commercePriceListDiscountRel) {

		entityCache.removeResult(
			CommercePriceListDiscountRelImpl.class,
			commercePriceListDiscountRel);
	}

	@Override
	public void clearCache(
		List<CommercePriceListDiscountRel> commercePriceListDiscountRels) {

		for (CommercePriceListDiscountRel commercePriceListDiscountRel :
				commercePriceListDiscountRels) {

			entityCache.removeResult(
				CommercePriceListDiscountRelImpl.class,
				commercePriceListDiscountRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommercePriceListDiscountRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommercePriceListDiscountRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceListDiscountRelModelImpl
			commercePriceListDiscountRelModelImpl) {

		Object[] args = new Object[] {
			commercePriceListDiscountRelModelImpl.getCommerceDiscountId(),
			commercePriceListDiscountRelModelImpl.getCommercePriceListId()
		};

		finderCache.putResult(_finderPathCountByC_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C, args, commercePriceListDiscountRelModelImpl);
	}

	/**
	 * Creates a new commerce price list discount rel with the primary key. Does not add the commerce price list discount rel to the database.
	 *
	 * @param commercePriceListDiscountRelId the primary key for the new commerce price list discount rel
	 * @return the new commerce price list discount rel
	 */
	@Override
	public CommercePriceListDiscountRel create(
		long commercePriceListDiscountRelId) {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			new CommercePriceListDiscountRelImpl();

		commercePriceListDiscountRel.setNew(true);
		commercePriceListDiscountRel.setPrimaryKey(
			commercePriceListDiscountRelId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceListDiscountRel.setUuid(uuid);

		commercePriceListDiscountRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commercePriceListDiscountRel;
	}

	/**
	 * Removes the commerce price list discount rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel that was removed
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListDiscountRel remove(
			long commercePriceListDiscountRelId)
		throws NoSuchPriceListDiscountRelException {

		return remove((Serializable)commercePriceListDiscountRelId);
	}

	/**
	 * Removes the commerce price list discount rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel that was removed
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListDiscountRel remove(Serializable primaryKey)
		throws NoSuchPriceListDiscountRelException {

		Session session = null;

		try {
			session = openSession();

			CommercePriceListDiscountRel commercePriceListDiscountRel =
				(CommercePriceListDiscountRel)session.get(
					CommercePriceListDiscountRelImpl.class, primaryKey);

			if (commercePriceListDiscountRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceListDiscountRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commercePriceListDiscountRel);
		}
		catch (NoSuchPriceListDiscountRelException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CommercePriceListDiscountRel removeImpl(
		CommercePriceListDiscountRel commercePriceListDiscountRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceListDiscountRel)) {
				commercePriceListDiscountRel =
					(CommercePriceListDiscountRel)session.get(
						CommercePriceListDiscountRelImpl.class,
						commercePriceListDiscountRel.getPrimaryKeyObj());
			}

			if (commercePriceListDiscountRel != null) {
				session.delete(commercePriceListDiscountRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListDiscountRel != null) {
			clearCache(commercePriceListDiscountRel);
		}

		return commercePriceListDiscountRel;
	}

	@Override
	public CommercePriceListDiscountRel updateImpl(
		CommercePriceListDiscountRel commercePriceListDiscountRel) {

		boolean isNew = commercePriceListDiscountRel.isNew();

		if (!(commercePriceListDiscountRel instanceof
				CommercePriceListDiscountRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commercePriceListDiscountRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePriceListDiscountRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePriceListDiscountRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePriceListDiscountRel implementation " +
					commercePriceListDiscountRel.getClass());
		}

		CommercePriceListDiscountRelModelImpl
			commercePriceListDiscountRelModelImpl =
				(CommercePriceListDiscountRelModelImpl)
					commercePriceListDiscountRel;

		if (Validator.isNull(commercePriceListDiscountRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commercePriceListDiscountRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commercePriceListDiscountRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePriceListDiscountRel.setCreateDate(date);
			}
			else {
				commercePriceListDiscountRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePriceListDiscountRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePriceListDiscountRel.setModifiedDate(date);
			}
			else {
				commercePriceListDiscountRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commercePriceListDiscountRel);
			}
			else {
				commercePriceListDiscountRel =
					(CommercePriceListDiscountRel)session.merge(
						commercePriceListDiscountRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommercePriceListDiscountRelImpl.class,
			commercePriceListDiscountRelModelImpl, false, true);

		cacheUniqueFindersCache(commercePriceListDiscountRelModelImpl);

		if (isNew) {
			commercePriceListDiscountRel.setNew(false);
		}

		commercePriceListDiscountRel.resetOriginalValues();

		return commercePriceListDiscountRel;
	}

	/**
	 * Returns the commerce price list discount rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListDiscountRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchPriceListDiscountRelException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			fetchByPrimaryKey(primaryKey);

		if (commercePriceListDiscountRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceListDiscountRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commercePriceListDiscountRel;
	}

	/**
	 * Returns the commerce price list discount rel with the primary key or throws a <code>NoSuchPriceListDiscountRelException</code> if it could not be found.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListDiscountRel findByPrimaryKey(
			long commercePriceListDiscountRelId)
		throws NoSuchPriceListDiscountRelException {

		return findByPrimaryKey((Serializable)commercePriceListDiscountRelId);
	}

	/**
	 * Returns the commerce price list discount rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel, or <code>null</code> if a commerce price list discount rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListDiscountRel fetchByPrimaryKey(
		long commercePriceListDiscountRelId) {

		return fetchByPrimaryKey((Serializable)commercePriceListDiscountRelId);
	}

	/**
	 * Returns all the commerce price list discount rels.
	 *
	 * @return the commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list discount rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @return the range of commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list discount rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list discount rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list discount rels
	 */
	@Override
	public List<CommercePriceListDiscountRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListDiscountRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CommercePriceListDiscountRel> list = null;

		if (useFinderCache) {
			list = (List<CommercePriceListDiscountRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL;

				sql = sql.concat(
					CommercePriceListDiscountRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommercePriceListDiscountRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the commerce price list discount rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceListDiscountRel commercePriceListDiscountRel :
				findAll()) {

			remove(commercePriceListDiscountRel);
		}
	}

	/**
	 * Returns the number of commerce price list discount rels.
	 *
	 * @return the number of commerce price list discount rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEPRICELISTDISCOUNTREL);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "commercePriceListDiscountRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommercePriceListDiscountRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce price list discount rel persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceListDiscountRelPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommercePriceListDiscountRelModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommercePriceListId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commercePriceListId"}, true);

		_finderPathWithoutPaginationFindByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceListId", new String[] {Long.class.getName()},
			new String[] {"commercePriceListId"}, true);

		_finderPathCountByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceListId", new String[] {Long.class.getName()},
			new String[] {"commercePriceListId"}, false);

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceDiscountId", "commercePriceListId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceDiscountId", "commercePriceListId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommercePriceListDiscountRelImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL =
		"SELECT commercePriceListDiscountRel FROM CommercePriceListDiscountRel commercePriceListDiscountRel";

	private static final String _SQL_SELECT_COMMERCEPRICELISTDISCOUNTREL_WHERE =
		"SELECT commercePriceListDiscountRel FROM CommercePriceListDiscountRel commercePriceListDiscountRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEPRICELISTDISCOUNTREL =
		"SELECT COUNT(commercePriceListDiscountRel) FROM CommercePriceListDiscountRel commercePriceListDiscountRel";

	private static final String _SQL_COUNT_COMMERCEPRICELISTDISCOUNTREL_WHERE =
		"SELECT COUNT(commercePriceListDiscountRel) FROM CommercePriceListDiscountRel commercePriceListDiscountRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commercePriceListDiscountRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommercePriceListDiscountRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePriceListDiscountRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListDiscountRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "order"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommercePriceListDiscountRelModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CommercePriceListDiscountRelModelImpl
				commercePriceListDiscountRelModelImpl =
					(CommercePriceListDiscountRelModelImpl)baseModel;

			long columnBitmask =
				commercePriceListDiscountRelModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commercePriceListDiscountRelModelImpl, columnNames,
					original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commercePriceListDiscountRelModelImpl.getColumnBitmask(
							columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commercePriceListDiscountRelModelImpl, columnNames,
					original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommercePriceListDiscountRelImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommercePriceListDiscountRelTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			CommercePriceListDiscountRelModelImpl
				commercePriceListDiscountRelModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commercePriceListDiscountRelModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						commercePriceListDiscountRelModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |=
				CommercePriceListDiscountRelModelImpl.getColumnBitmask(
					"order_");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}