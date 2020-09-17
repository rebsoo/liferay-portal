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

import {waitForElementToBeRemoved} from '@testing-library/dom';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import React from 'react';

import ListFormViews from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/ListFormViews.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import {DATA_DEFINITION_RESPONSES, RESPONSES} from '../../constants.es';

describe('ListFormViews', () => {
	let spyFromNow;
	let PortletURL;

	beforeEach(() => {
		PortletURL = {
			createRenderURL: jest.fn(),
		};

		window.Liferay = {
			...window.Liferay,
			Util: {
				...window.Liferay.Util,
				PortletURL,
			},
		};
		spyFromNow = jest
			.spyOn(time, 'fromNow')
			.mockImplementation(() => 'months ago');
	});

	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('renders', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const {asFragment} = render(
			<AppContextProviderWrapper
				appContext={{basePortletURL: 'localhost'}}
			>
				<ListFormViews
					match={{
						params: {
							dataDefinitionId: 1,
						},
					}}
				/>
			</AppContextProviderWrapper>
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with empty state', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.NO_ITEMS))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const {container, queryByText} = render(
			<AppContextProviderWrapper
				appContext={{basePortletURL: 'localhost'}}
			>
				<ListFormViews
					match={{
						params: {
							dataDefinitionId: 1,
						},
					}}
				/>
			</AppContextProviderWrapper>
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			queryByText(
				'create-one-or-more-forms-to-display-the-data-held-in-your-data-object'
			)
		).toBeTruthy();

		expect(queryByText('there-are-no-form-views-yet')).toBeTruthy();

		const buttonNewFormView = container.querySelector(
			'[data-title="new-form-view"]'
		);

		expect(buttonNewFormView).toBeTruthy();

		expect(PortletURL.createRenderURL.mock.calls[0][0]).toEqual(
			'localhost'
		);

		expect(fetch.mock.calls.length).toEqual(2);
	});

	xit('renders with data and click on actions', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const history = createMemoryHistory();

		const {baseElement, container, debug, queryByText} = render(
			<AppContextProviderWrapper
				appContext={{basePortletURL: 'localhost'}}
				history={history}
			>
				<ListFormViews
					match={{
						params: {
							dataDefinitionId: 1,
						},
					}}
				/>
			</AppContextProviderWrapper>
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);
	});
});
