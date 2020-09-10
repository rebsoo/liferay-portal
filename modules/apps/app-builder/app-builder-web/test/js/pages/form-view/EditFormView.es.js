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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {DataLayoutBuilder} from 'data-engine-taglib';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {AppContextProvider} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import EditFormView from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/EditFormView.es';
import * as toast from '../../../../src/main/resources/META-INF/resources/js/utils/toast.es';
import {DATA_DEFINITION_RESPONSES} from '../../constants.es';

const initialState = {
	appProps: {},
	config: {
		allowFieldSets: false,
		allowNestedFields: true,
		allowRules: false,
		disabledProperties: [],
		disabledTabs: [],
		multiPage: true,
		ruleSettings: {},
		unimplementedProperties: [],
	},
	dataDefinition: DATA_DEFINITION_RESPONSES.ONE_ITEM,
	dataDefinitionId: 0,
	dataLayout: {
		dataLayoutPages: [],
		dataRules: [],
		name: {},
		paginationMode: 'wizard',
	},
	dataLayoutId: 0,
	editingDataDefinitionId: 0,
	editingLanguageId: themeDisplay.getLanguageId(),
	fieldSets: [],
	fieldTypes: [
		{
			name: 'date',
			label: 'Date',
			scope: 'app-builder,forms',
		},
		{
			name: 'select',
			label: 'Select from List',
			scope: 'app-builder,forms',
		},
		{
			name: 'fieldset',
			label: 'Fields Group',
			scope: 'app-builder,forms',
		},
		{
			name: 'numeric',
			label: 'Numeric',
			scope: 'app-builder,forms',
		},
		{
			name: 'checkbox_multiple',
			label: 'Multiple Selection',
			scope: 'app-builder,forms',
		},
		{
			name: 'radio',
			label: 'Single Selection',
			scope: 'app-builder,forms',
		},
		{
			name: 'text',
			label: 'Text',
			scope: 'app-builder,forms',
		},
		{
			name: 'document_library',
			label: 'Upload',
			scope: 'app-builder,forms',
		},
	],
	focusedCustomObjectField: {},
	focusedField: {},
	hoveredField: {},
	sidebarOpen: true,
	sidebarPanelId: 'fields',
	spritemap: `${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg`,
};

const props = {
	appContext: [
		{},
		(action) => {
			props.appContext[0].action = action;
		},
	],
	config: {
		allowFieldSets: true,
		allowMultiplePages: false,
		allowRules: false,
		allowSuccessPage: false,
		disabledProperties: ['predefinedValue'],
		disabledTabs: ['Autocomplete'],
		unimplementedProperties: [
			'fieldNamespace',
			'indexType',
			'readOnly',
			'validation',
			'visibilityExpression',
		],
	},
	context: {},
	dataLayoutBuilderId:
		'_com_liferay_journal_web_portlet_JournalPortlet_dataLayoutBuilder',
	fieldTypes: [],
	localizable: true,
	portletNamespace: 'com_liferay_journal_web_portlet_JournalPortlet',
};

const dataLayoutBuilder = new DataLayoutBuilder.default(props);

const dataLayoutBuilderProps = {
	...dataLayoutBuilder,
	dispatchAction: jest.fn(),
	getLayoutProvider: () => ({
		getEvents: () => ({
			fieldHovered: jest.fn(),
		}),
	}),
	getState: () => {
		return initialState;
	},
	on: jest.fn(),
	onEditingLanguageIdChange: jest.fn(),
	removeEventListener: jest.fn(),
};

function componentReady() {
	return new Promise((resolve) => {
		resolve(dataLayoutBuilderProps);
	});
}

const AppContextProviderWrapper = (props) => <AppContextProvider {...props} />;
describe('EditFormView', () => {
	let spySuccessToast;
	let spyErrorToast;
	beforeEach(() => {
		jest.useFakeTimers();
		window.Liferay = {
			...window.Liferay,
			componentReady,
		};
		spySuccessToast = jest
			.spyOn(toast, 'successToast')
			.mockImplementation();
		spyErrorToast = jest.spyOn(toast, 'errorToast').mockImplementation();
	});

	afterEach(() => {
		cleanup();
		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	xit('renders', async () => {
		const {asFragment, debug} = render(
			<DndProvider backend={HTML5Backend}>
				<EditFormView />
			</DndProvider>,
			{
				wrapper: AppContextProviderWrapper,
			}
		);
		await act(async () => {
			jest.runAllTimers();
		});
		expect(asFragment()).toMatchSnapshot();
		debug();
	});

	it('renders', async () => {
		const props = {
			basePortletURL: `localhost`,
			customObjectSidebarElementId: `customObject`,
			dataDefinitionId: 1,
			dataLayoutBuilderElementId: ``,
			dataLayoutBuilderId: 1,
			dataLayoutId: 1,
			newCustomObject: true,
		};
		const {debug, queryByText} = render(
			<AppContextProviderWrapper>
				<div className="tools-control-group">
					<div className="control-menu-level-1-heading" />
				</div>
				<DndProvider backend={HTML5Backend}>
					<div id={props.customObjectSidebarElementId} />
					<EditFormView {...props} />
				</DndProvider>
			</AppContextProviderWrapper>
		);
		await act(async () => {
			jest.runAllTimers();
		});

		const Date = queryByText('Date');

		fireEvent.click(Date);

		console.log(dataLayoutBuilderProps.dispatchAction.mock.calls);

		debug();
	});
});
