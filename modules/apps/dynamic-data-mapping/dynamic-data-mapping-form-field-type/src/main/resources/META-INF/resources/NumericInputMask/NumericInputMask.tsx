import React from 'react';

//@ts-ignore
import Select from '../Select/Select.es';
//@ts-ignore
import Text from '../Text/Text.es';
//@ts-ignore
import Radio from '../Radio/Radio.es';

type DecimalSeparator = ',' | '.';
type ThousandsSeparator = DecimalSeparator | ' ' | "'" | 'none';


interface INumericInputMaskValue {
    append?: string;
    appendType?: 'prefix' | 'suffix';
    decimalSeparator: DecimalSeparator[];
    thousandsSeparator?: ThousandsSeparator[];

}
interface IProps {
    decimalSeparators: ISelectProps<DecimalSeparator>[];
    onChange: (event: { target: { value: string } }) => void;
    readOnly: boolean;
    thousandsSeparators: ISelectProps<ThousandsSeparator>[];
    value: INumericInputMaskValue;
    visible: boolean;
}

interface ISelectProps<T> {
    checked: boolean,
    label: string,
    value: T;
}

const NumericInputMask: React.FC<IProps> = ({
    decimalSeparators,
    onChange,
    readOnly,
    thousandsSeparators,
    value: valueProp,
    visible,
}) => {

    const handleChange = (attributeName: keyof INumericInputMaskValue) => (event: React.ChangeEvent<HTMLInputElement>) => {
        const inputValue = event.target.value;
        const value = {
            ...valueProp,
            [attributeName]: typeof inputValue === 'string' ? inputValue : inputValue[0],
        };

        onChange({ target: { value: JSON.stringify(value) } });
    }


    return (
        <>
            <Select
                //@ts-ignore
                label={Liferay.Language.get('thousands-separator')}
                name="thousandsSeparator"
                onChange={handleChange('thousandsSeparator')}
                options={thousandsSeparators}
                //@ts-ignore
                placeholder={Liferay.Language.get('choose-an-option')}
                readOnly={readOnly}
                showEmptyOption={false}
                value={valueProp.thousandsSeparator}
                visible={visible}
            />
            <Select
                //@ts-ignore
                label={Liferay.Language.get('decimal-separator')}
                name="decimalSeparator"
                onChange={handleChange('decimalSeparator')}
                options={decimalSeparators}
                //@ts-ignore
                placeholder={Liferay.Language.get('choose-an-option')}
                readOnly={readOnly}
                showEmptyOption={false}
                value={valueProp.decimalSeparator}
                visible={visible}
            />
            <Text
                //@ts-ignore
                label={Liferay.Language.get('prefix-or-suffix')}
                name="append"
                onChange={handleChange('append')}
                //@ts-ignore
                placeholder={Liferay.Language.get('input-mask-append-placeholder')}
                readOnly={readOnly}
                required={false}
                value={valueProp.append}
                visible={visible}
            />
            <Radio
                inline={false}
                name="appendType"
                onChange={handleChange('appendType')}
                options={[
                    {
                        //@ts-ignore
                        label: Liferay.Language.get('suffix'),
                        value: 'suffix',
                    },
                    {
                        //@ts-ignore
                        label: Liferay.Language.get('prefix'),
                        value: 'prefix',
                    },
                ]}
                readOnly={readOnly}
                value={valueProp.appendType}
            />

            <input type="hidden" value={JSON.stringify(valueProp)} />

        </>
    );

}

export default NumericInputMask;