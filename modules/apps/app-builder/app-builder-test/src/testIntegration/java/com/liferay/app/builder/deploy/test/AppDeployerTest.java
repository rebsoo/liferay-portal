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

package com.liferay.app.builder.deploy.test;

import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.deploy.AppDeployerTracker;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppDeploymentLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Rebeca Silva
 * @author Alejo Ceballos
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AppDeployerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeployProductMenuInSiteAndApplications() throws Exception {
		AppBuilderApp appBuilderApp = _createAppBuilderApp();

		String siteValue = "site_administration.content";

		JSONObject siteJSONObject = JSONFactoryUtil.createJSONObject(
			JSONUtil.put(
				"scope", JSONUtil.put(siteValue)
			).put(
				"siteIds", JSONUtil.put(-1)
			).toJSONString());

		_appBuilderAppDeploymentLocalService.addAppBuilderAppDeployment(
			appBuilderApp.getAppBuilderAppId(), siteJSONObject.toJSONString(),
			"productMenu");

		_deployAndUndeployAppBuilderApp(appBuilderApp, siteValue);

		String applicationsValue = "applications_menu.applications";

		JSONObject applicationsJSONObject = JSONFactoryUtil.createJSONObject(
			JSONUtil.put(
				"scope", JSONUtil.put(applicationsValue)
			).toJSONString());

		_appBuilderAppDeploymentLocalService.addAppBuilderAppDeployment(
			appBuilderApp.getAppBuilderAppId(),
			applicationsJSONObject.toJSONString(), "productMenu");

		_deployAndUndeployAppBuilderApp(appBuilderApp, applicationsValue);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		JSONObject siteAndApplicationsJSONObject =
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					"scope",
					jsonArray.put(
						applicationsValue
					).put(
						siteValue
					)
				).put(
					"siteIds", JSONUtil.put(-1)
				).toJSONString());

		_appBuilderAppDeploymentLocalService.addAppBuilderAppDeployment(
			appBuilderApp.getAppBuilderAppId(),
			siteAndApplicationsJSONObject.toJSONString(), "productMenu");

		_deployAndUndeployAppBuilderApp(
			appBuilderApp, applicationsValue, siteValue);
	}

	private AppBuilderApp _createAppBuilderApp() throws Exception {
		return _appBuilderAppLocalService.addAppBuilderApp(
			TestPropsValues.getGroupId(), TestPropsValues.getCompanyId(),
			TestPropsValues.getUserId(), true, RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString());
	}

	private void _deployAndUndeployAppBuilderApp(
			AppBuilderApp appBuilderApp, String... expectedPanelCategoryKeys)
		throws Exception {

		AppDeployer appDeployer = _appDeployerTracker.getAppDeployer(
			"productMenu");

		appDeployer.deploy(appBuilderApp.getAppBuilderAppId());

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		Map<Long, ServiceRegistration<?>[]> serviceRegistrationsMap =
			ReflectionTestUtil.getFieldValue(
				appDeployer, "_serviceRegistrations");

		ServiceRegistration<?>[] serviceRegistrations =
			serviceRegistrationsMap.get(appBuilderApp.getAppBuilderAppId());

		List<String> panelCategoryKeys = new ArrayList<>();

		List<ServiceReference<?>> serviceReferences = new ArrayList<>();

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			ServiceReference<?> serviceReference =
				serviceRegistration.getReference();

			Assert.assertNotNull(bundleContext.getService(serviceReference));

			String panelCategoryKey = (String)serviceReference.getProperty(
				"panel.category.key");

			if (panelCategoryKey != null) {
				panelCategoryKeys.add(panelCategoryKey);
			}

			serviceReferences.add(serviceReference);
		}

		List<String> assertPanelCategoryKeys = Arrays.asList(
			expectedPanelCategoryKeys);

		Assert.assertTrue(
			(assertPanelCategoryKeys.size() == panelCategoryKeys.size()) &&
			assertPanelCategoryKeys.containsAll(panelCategoryKeys) &&
			panelCategoryKeys.containsAll(assertPanelCategoryKeys));

		appDeployer.undeploy(appBuilderApp.getAppBuilderAppId());

		Assert.assertNotNull(
			_appBuilderAppLocalService.fetchAppBuilderApp(
				appBuilderApp.getAppBuilderAppId()));

		for (ServiceReference<?> serviceReference : serviceReferences) {
			Assert.assertNull(bundleContext.getService(serviceReference));
		}
	}

	@Inject
	private AppBuilderAppDeploymentLocalService
		_appBuilderAppDeploymentLocalService;

	@Inject
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Inject
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

	@Inject
	private AppDeployerTracker _appDeployerTracker;

}