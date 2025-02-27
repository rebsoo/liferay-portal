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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectDefinitionServiceUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class ObjectDefinitionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());
		_user = TestPropsValues.getUser();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testAddCustomObjectDefinition() throws Exception {
		try {
			_testAddCustomObjectDefinition(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have ADD_ENTRY permission for"));
		}

		_testAddCustomObjectDefinition(_user);
	}

	@Test
	public void testDeleteObjectDefinition() throws Exception {
		try {
			_testDeleteObjectDefinition(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have DELETE permission for"));
		}

		_testDeleteObjectDefinition(_user);
	}

	@Test
	public void testGetObjectDefinition() throws Exception {
		try {
			_testGetObjectDefinition(_defaultUser);
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetObjectDefinition(_user);
	}

	private ObjectDefinition _addCustomObjectDefinition(User user)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				user.getUserId(), "Test", null);

		return ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			user.getUserId(), objectDefinition.getObjectDefinitionId());
	}

	private void _testAddCustomObjectDefinition(User user) throws Exception {
		ObjectDefinition objectDefinition = null;

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			objectDefinition =
				ObjectDefinitionServiceUtil.addCustomObjectDefinition(
					user.getUserId(), "Test");

			objectDefinition =
				ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
					user.getUserId(), objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (objectDefinition != null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testDeleteObjectDefinition(User user) throws Exception {
		ObjectDefinition deleteObjectDefinition = null;
		ObjectDefinition objectDefinition = null;

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			objectDefinition = _addCustomObjectDefinition(user);

			deleteObjectDefinition =
				ObjectDefinitionServiceUtil.deleteObjectDefinition(
					objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (deleteObjectDefinition == null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testGetObjectDefinition(User user) throws Exception {
		ObjectDefinition objectDefinition = null;

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			objectDefinition = _addCustomObjectDefinition(user);

			ObjectDefinitionServiceUtil.getObjectDefinition(
				objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (objectDefinition != null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private User _defaultUser;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}