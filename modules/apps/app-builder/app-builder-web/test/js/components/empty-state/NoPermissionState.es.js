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

import { act, render } from '@testing-library/react';
import React from 'react';

import NoPermissionState from '../../../../src/main/resources/META-INF/resources/js/components/empty-state/NoPermissionState.es';
import { FORM_VIEW } from '../../constants.es';
const { EDIT_FORM_VIEW_PROPS, getDataLayoutBuilderProps } = FORM_VIEW;

// const noPermission = {
//     description: 'description',
//     title: 'title',
// }

describe('NoPermissionState', () => {
    let dataLayoutBuilderProps;

    beforeEach(() => {
        jest.useFakeTimers();

        dataLayoutBuilderProps = getDataLayoutBuilderProps();

        window.Liferay = {
            ...window.Liferay,
            componentReady: () =>
                new Promise((resolve) => resolve(dataLayoutBuilderProps)),
        };
    });

    it('renders', async () => {
        const { asFragment, debug } = render(
            <NoPermissionState />

        );

        await act(async () => {
            jest.runAllTimers();
        });

        expect(asFragment()).toMatchSnapshot();

        debug();
    });
});
