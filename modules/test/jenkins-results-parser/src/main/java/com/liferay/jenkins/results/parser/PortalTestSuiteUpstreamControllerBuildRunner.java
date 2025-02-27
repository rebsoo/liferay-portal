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

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PortalTestSuiteUpstreamControllerBuildRunner
	<S extends PortalTestSuiteUpstreamControllerBuildData>
		extends BaseBuildRunner<S, Workspace> {

	@Override
	public void run() {
		keepJenkinsBuild(true);

		invokeTestSuiteBuilds();
	}

	@Override
	public void tearDown() {
	}

	protected PortalTestSuiteUpstreamControllerBuildRunner(S buildData) {
		super(buildData);
	}

	protected String getInvocationCohortName() {
		String invocationCohortName = System.getenv("INVOCATION_COHORT_NAME");

		if ((invocationCohortName != null) && !invocationCohortName.isEmpty()) {
			return invocationCohortName;
		}

		BuildData buildData = getBuildData();

		return buildData.getCohortName();
	}

	protected String getJobURL() {
		String mostAvailableMasterURL =
			JenkinsResultsParserUtil.getMostAvailableMasterURL(
				JenkinsResultsParserUtil.combine(
					"http://" + getInvocationCohortName() + ".liferay.com"),
				1);

		S buildData = getBuildData();

		return JenkinsResultsParserUtil.combine(
			mostAvailableMasterURL, "/job/test-portal-testsuite-upstream(",
			buildData.getPortalUpstreamBranchName(), ")");
	}

	protected String getTestPortalBuildProfile(String testSuite) {
		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			S buildData = getBuildData();

			String buildProfile = buildProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.test.portal.build.profile[",
					buildData.getPortalUpstreamBranchName(), "][", testSuite,
					"]"));

			if (buildProfile == null) {
				buildProfile = buildProperties.getProperty(
					"portal.testsuite.upstream.test.portal.build.profile");
			}

			return buildProfile;
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get portal build profile for test suite " +
					testSuite,
				ioException);
		}
	}

	@Override
	protected void initWorkspace() {
		setWorkspace(WorkspaceFactory.newSimpleWorkspace());
	}

	protected void invokeTestSuiteBuilds() {
		List<String> testSuiteNames = _getSelectedTestSuiteNames();

		if (testSuiteNames.isEmpty()) {
			System.out.println("There are no test suites to run at this time.");

			return;
		}

		String jenkinsAuthenticationToken;

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			jenkinsAuthenticationToken = buildProperties.getProperty(
				"jenkins.authentication.token");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		S buildData = getBuildData();

		for (String testSuiteName : testSuiteNames) {
			String jobURL = getJobURL();

			StringBuilder sb = new StringBuilder();

			sb.append(jobURL);
			sb.append("/buildWithParameters?");
			sb.append("token=");
			sb.append(jenkinsAuthenticationToken);

			Map<String, String> invocationParameters = new HashMap<>();

			invocationParameters.put("CI_TEST_SUITE", testSuiteName);
			invocationParameters.put(
				"JENKINS_GITHUB_BRANCH_NAME",
				buildData.getJenkinsGitHubBranchName());
			invocationParameters.put(
				"JENKINS_GITHUB_BRANCH_USERNAME",
				buildData.getJenkinsGitHubUsername());
			invocationParameters.put(
				"PORTAL_GIT_COMMIT", buildData.getPortalBranchSHA());
			invocationParameters.put(
				"PORTAL_GITHUB_URL", buildData.getPortalGitHubURL());

			String testPortalBuildProfile = getTestPortalBuildProfile(
				testSuiteName);

			if (testPortalBuildProfile != null) {
				invocationParameters.put(
					"TEST_PORTAL_BUILD_PROFILE", testPortalBuildProfile);
			}

			String testrayProjectName = _getTestrayProjectName(testSuiteName);

			if (testrayProjectName != null) {
				String testrayRoutineName = JenkinsResultsParserUtil.combine(
					"[", buildData.getPortalUpstreamBranchName(), "] ci:test:",
					testSuiteName);

				String testraybuildName = JenkinsResultsParserUtil.combine(
					testrayRoutineName, " - ",
					String.valueOf(buildData.getBuildNumber()), " - ",
					JenkinsResultsParserUtil.toDateString(
						new Date(buildData.getStartTime()),
						"yyyy-MM-dd[HH:mm:ss]", "America/Los_Angeles"));

				if (_getTestrayRoutineName(testSuiteName) != null) {
					testrayRoutineName = _getTestrayRoutineName(testSuiteName);
				}

				invocationParameters.put(
					"TESTRAY_BUILD_NAME", testraybuildName);
				invocationParameters.put(
					"TESTRAY_PROJECT_NAME", testrayProjectName);
				invocationParameters.put(
					"TESTRAY_ROUTINE_NAME", testrayRoutineName);
			}

			invocationParameters.putAll(buildData.getBuildParameters());

			for (Map.Entry<String, String> invocationParameter :
					invocationParameters.entrySet()) {

				if (invocationParameter.getValue() == null) {
					continue;
				}

				sb.append("&");
				sb.append(invocationParameter.getKey());
				sb.append("=");
				sb.append(invocationParameter.getValue());
			}

			try {
				JenkinsResultsParserUtil.toString(sb.toString());

				System.out.println(
					"Job for '" + testSuiteName + "' was invoked at " + jobURL);

				_invokedTestSuiteNames.add(testSuiteName);
			}
			catch (IOException ioException) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Unable to invoke a new build for test suite, '",
						testSuiteName, "'"));

				ioException.printStackTrace();
			}
		}

		buildData.setBuildDescription(
			JenkinsResultsParserUtil.join(", ", _invokedTestSuiteNames));

		updateBuildDescription();
	}

	private List<Build> _getBuildHistory() {
		S buildData = getBuildData();

		Build build = BuildFactory.newBuild(buildData.getBuildURL(), null);

		Job job = JobFactory.newJob(buildData.getJobName());

		return job.getBuildHistory(build.getJenkinsMaster());
	}

	private List<String> _getBuildTestSuiteNames(Build build) {
		String buildDescription = build.getBuildDescription();

		if ((buildDescription == null) || buildDescription.isEmpty()) {
			return Collections.emptyList();
		}

		return Arrays.asList(buildDescription.split("\\s*,\\s*"));
	}

	private Map<String, Long> _getCandidateTestSuiteStaleDurations() {
		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		S buildData = getBuildData();

		String upstreamBranchName = buildData.getPortalUpstreamBranchName();

		Map<String, Long> candidateTestSuiteStaleDurations =
			new LinkedHashMap<>();

		for (String testSuiteName : _getTestSuiteNames()) {
			String suiteStaleDuration = buildProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.stale.duration[",
					upstreamBranchName, "][", testSuiteName, "]"));

			if (suiteStaleDuration == null) {
				continue;
			}

			candidateTestSuiteStaleDurations.put(
				testSuiteName, Long.parseLong(suiteStaleDuration) * 60 * 1000);
		}

		return candidateTestSuiteStaleDurations;
	}

	private Map<String, Long> _getLatestTestSuiteStartTimes() {
		List<Build> builds = _getBuildHistory();

		BuildData buildData = getBuildData();

		Build currentBuild = BuildFactory.newBuild(
			buildData.getBuildURL(), null);

		builds.remove(currentBuild);

		Map<String, Long> latestTestSuiteStartTimes = new LinkedHashMap<>();

		for (String testSuiteName : _getTestSuiteNames()) {
			for (Build build : builds) {
				List<String> buildTestSuiteNames = _getBuildTestSuiteNames(
					build);

				if (buildTestSuiteNames.contains(testSuiteName)) {
					latestTestSuiteStartTimes.put(
						testSuiteName, build.getStartTime());

					break;
				}
			}
		}

		return latestTestSuiteStartTimes;
	}

	private List<String> _getSelectedTestSuiteNames() {
		if (_selectedTestSuiteNames != null) {
			return _selectedTestSuiteNames;
		}

		_selectedTestSuiteNames = new ArrayList<>();

		Map<String, Long> candidateTestSuiteStaleDurations =
			_getCandidateTestSuiteStaleDurations();

		Map<String, Long> latestTestSuiteStartTimes =
			_getLatestTestSuiteStartTimes();

		S buildData = getBuildData();

		Long startTime = buildData.getStartTime();

		for (Map.Entry<String, Long> entry :
				candidateTestSuiteStaleDurations.entrySet()) {

			String testSuiteName = entry.getKey();

			if (!latestTestSuiteStartTimes.containsKey(testSuiteName)) {
				_selectedTestSuiteNames.add(testSuiteName);

				continue;
			}

			Long testSuiteIdleDuration =
				startTime - latestTestSuiteStartTimes.get(testSuiteName);

			if (testSuiteIdleDuration > entry.getValue()) {
				_selectedTestSuiteNames.add(testSuiteName);
			}
		}

		return _selectedTestSuiteNames;
	}

	private String _getTestrayProjectName(String testSuite) {
		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			S buildData = getBuildData();

			return buildProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.testray.project.name[",
					buildData.getPortalUpstreamBranchName(), "][", testSuite,
					"]"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private String _getTestrayRoutineName(String testSuite) {
		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			S buildData = getBuildData();

			String testrayRoutineName = JenkinsResultsParserUtil.getProperty(
				buildProperties,
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.testray.routine.name[",
					buildData.getPortalUpstreamBranchName(), "][", testSuite,
					"]"));

			if (JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {
				testrayRoutineName = JenkinsResultsParserUtil.getProperty(
					buildProperties,
					JenkinsResultsParserUtil.combine(
						"portal.testsuite.upstream.testray.build.type[",
						buildData.getPortalUpstreamBranchName(), "][",
						testSuite, "]"));
			}

			return testrayRoutineName;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private List<String> _getTestSuiteNames() {
		S buildData = getBuildData();

		try {
			return JenkinsResultsParserUtil.getBuildPropertyAsList(
				true,
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.suites[",
					buildData.getPortalUpstreamBranchName(), "]"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final List<String> _invokedTestSuiteNames = new ArrayList<>();
	private List<String> _selectedTestSuiteNames;

}