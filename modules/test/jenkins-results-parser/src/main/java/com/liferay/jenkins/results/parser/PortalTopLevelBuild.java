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

package com.liferay.jenkins.results.parser;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class PortalTopLevelBuild
	extends DefaultTopLevelBuild
	implements AnalyticsCloudBranchInformationBuild,
			   PluginsBranchInformationBuild, PortalBranchInformationBuild,
			   PortalFixpackReleaseBuild, PortalReleaseBuild {

	public PortalTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	public String getBaseGitRepositoryName() {
		String branchName = getBranchName();

		if (branchName.equals("master")) {
			return "liferay-portal";
		}

		return "liferay-portal-ee";
	}

	@Override
	public BranchInformation getOSBAsahBranchInformation() {
		return getBranchInformation("osb.asah");
	}

	@Override
	public BranchInformation getOSBFaroBranchInformation() {
		return getBranchInformation("osb.faro");
	}

	@Override
	public BranchInformation getPluginsBranchInformation() {
		return getBranchInformation("plugins");
	}

	@Override
	public BranchInformation getPortalBaseBranchInformation() {
		BranchInformation portalBranchInformation =
			getPortalBranchInformation();

		if (portalBranchInformation == null) {
			return null;
		}

		String upstreamBranchName =
			portalBranchInformation.getUpstreamBranchName();

		if (upstreamBranchName.contains("-private")) {
			return getBranchInformation("portal.base");
		}

		return null;
	}

	@Override
	public BranchInformation getPortalBranchInformation() {
		return getBranchInformation("portal");
	}

	@Override
	public PortalFixpackRelease getPortalFixpackRelease() {
		if (portalFixpackRelease != null) {
			return portalFixpackRelease;
		}

		Build controllerBuild = getControllerBuild();

		if (controllerBuild == null) {
			return null;
		}

		String portalFixPackVersion = controllerBuild.getParameterValue(
			"PORTAL_FIX_PACK_VERSION");

		if (portalFixPackVersion != null) {
			portalFixpackRelease = new PortalFixpackRelease(
				portalFixPackVersion, getPortalRelease());

			return portalFixpackRelease;
		}

		String portalFixPackZipURL = controllerBuild.getParameterValue(
			"TEST_PORTAL_FIX_PACK_ZIP_URL");

		if (portalFixPackZipURL != null) {
			try {
				portalFixpackRelease = new PortalFixpackRelease(
					new URL(portalFixPackZipURL));

				return portalFixpackRelease;
			}
			catch (MalformedURLException malformedURLException) {
				throw new RuntimeException(malformedURLException);
			}
		}

		return portalFixpackRelease;
	}

	@Override
	public PortalRelease getPortalRelease() {
		if (portalRelease != null) {
			return portalRelease;
		}

		Build controllerBuild = getControllerBuild();

		if (controllerBuild == null) {
			return null;
		}

		String portalBundleVersion = controllerBuild.getParameterValue(
			"PORTAL_BUNDLE_VERSION");

		if (portalBundleVersion != null) {
			portalRelease = new PortalRelease(portalBundleVersion);

			return portalRelease;
		}

		PortalFixpackRelease portalFixpackRelease = getPortalFixpackRelease();

		if (portalFixpackRelease != null) {
			portalRelease = portalFixpackRelease.getPortalRelease();

			return portalRelease;
		}

		return portalRelease;
	}

	protected PortalFixpackRelease portalFixpackRelease;
	protected PortalRelease portalRelease;

}