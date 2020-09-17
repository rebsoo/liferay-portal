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
import {DataLayoutBuilder, DataLayoutVisitor} from 'data-engine-taglib';
import React, {useState, useCallback} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {ClayModalProvider} from '@clayui/modal';

import {AppContextProvider} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import EditFormView from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/EditFormView.es';
import FormViewContextProvider from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/FormViewContextProvider.es';
import FormViewContext from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/FormViewContext.es';
import useSaveAsFieldset from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/useSaveAsFieldset.es';
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
		name: {
			en_US: 'FormView',
		},
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

const dataLayoutBuilderConfig = {
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

const dataLayoutBuilder = new DataLayoutBuilder.default(
	dataLayoutBuilderConfig
);

const props = {
	basePortletURL: `localhost`,
	customObjectSidebarElementId: `customObject`,
	dataDefinitionId: 1,
	dataLayoutBuilderElementId: ``,
	dataLayoutBuilderId: 1,
	dataLayoutId: 1,
	newCustomObject: true,
};

const AppContextProviderWrapper = (props) => <AppContextProvider {...props} />;

describe('EditFormView', () => {
	let dataLayoutBuilderProps;
	let dataLayoutVisitorSpy;
	let successToastSpy;
	let errorToastSpy;

	beforeEach(() => {
		dataLayoutBuilderProps = {
			...dataLayoutBuilder,
			dispatchAction: jest.fn(),
			dispatch: jest.fn(),
			getDDMFormFieldSettingsContext: jest.fn(),
			getLayoutProvider: () => ({
				getEvents: () => ({
					fieldHovered: jest.fn(),
				}),
			}),
			getFieldTypes: () => {
				return [
					{
						name: 'Text',
					},
				];
			},
			getState: () => {
				return initialState;
			},
			getStore: () => {
				return {
					activePage: 0,
					pages: [
						{
							rows: [],
						},
					],
				};
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

		dataLayoutVisitorSpy = jest
			.spyOn(DataLayoutVisitor, 'isDataLayoutEmpty')
			.mockImplementation(() => false);

		successToastSpy = jest
			.spyOn(toast, 'successToast')
			.mockImplementation(() => {});
		errorToastSpy = jest
			.spyOn(toast, 'errorToast')
			.mockImplementation(() => {});

		jest.useFakeTimers();
		window.Liferay = {
			...window.Liferay,
			componentReady,
		};
	});

	afterEach(() => {
		cleanup();
		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	const RenderSaveAsFieldSet = ({onClick}) => {
		const saveAsFieldSet = useSaveAsFieldset({dataLayoutBuilder});
		return (
			<button
				onClick={() => {
					onClick();
					saveAsFieldSet('Text');
				}}
			>
				Save
			</button>
		);
	};

	const FormViewContextWrapper = ({
		dispatch = jest.fn(),
		children,
		defaultQuery = {},
	}) => {
		const [query, setQuery] = useState(defaultQuery);

		const defaultCallback = useCallback(
			(action) => {
				console.log(action);
				dispatch(action);
				setQuery(reducer(query, action));
			},
			// eslint-disable-next-line react-hooks/exhaustive-deps
			[query, setQuery]
		);

		return (
			<FormViewContext.Provider value={[query, defaultCallback]}>
				{children}
			</FormViewContext.Provider>
		);
	};

	it('renders', async () => {
		fetch.mockResponseOnce(
			JSON.stringify({
				defaultDataLayout: {
					defaultDataLayout: {
						id: 1,
					},
				},
			})
		);
		const dispatchFn = jest.fn();
		const onClick = jest.fn();

		const _dataLayoutBuilder = {
			...initialState,
			dispatch: jest.fn(),
		};

		const {debug, queryByText} = render(
			<FormViewContextWrapper
				dispatch={dispatchFn}
				defaultQuery={_dataLayoutBuilder}
			>
				<RenderSaveAsFieldSet onClick={onClick} />
			</FormViewContextWrapper>
		);

		const button = queryByText('Save');

		fireEvent.click(button);

		expect(onClick.mock.calls.length).toBe(1);
		expect(fetch.mock.calls.length).toBe(1);

		console.log(dispatchFn.mock);
		console.log(_dataLayoutBuilder.dispatch.mock);

		// expect()
		// expect(_dataLayoutBuilder.dispatch.mock.calls.length).toBe(1);
	});
});
