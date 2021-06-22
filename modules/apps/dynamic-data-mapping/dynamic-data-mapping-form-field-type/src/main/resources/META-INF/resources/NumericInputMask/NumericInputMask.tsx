import React from 'react';

//@ts-ignore
import Select from '../Select/Select.es';
//@ts-ignore
import Text from '../Text/Text.es';
//@ts-ignore
import Radio from '../Radio/Radio.es';

type DecimalSeparator = ',' | '.';
type ThousandsSeparator = DecimalSeparator | ' ' | "'" | 'none';

interface IProps {
    append?: string;
    appendType?: 'prefix' | 'suffix';
    decimalSeparator: DecimalSeparator[];
    decimalSeparators: ISelectProps<DecimalSeparator>[];
    readOnly: boolean;
    thousandsSeparator?: ThousandsSeparator[];
    thousandsSeparators: ISelectProps<ThousandsSeparator>[];
    visible: boolean;
}

interface ISelectProps<T> {
    checked: boolean,
    label: string,
    value: T;
}

const NumericInputMask: React.FC<IProps> = ({
    append,
    appendType,
    decimalSeparator,
    decimalSeparators,
    readOnly,
    thousandsSeparator,
    thousandsSeparators,
    visible,
}) => {
    return (
        <>
            <Select
                //@ts-ignore
                label={Liferay.Language.get('thousands-separator')}
                name="thousandsSeparator"
                // onChange={()=>{}}
                options={thousandsSeparators}
                //@ts-ignore
                placeholder={Liferay.Language.get('choose-an-option')}
                readOnly={readOnly}
                showEmptyOption={false}
                value={thousandsSeparator}
                visible={visible}
            />
            <Select
                //@ts-ignore
                label={Liferay.Language.get('decimal-separator')}
                name="decimalSeparator"
                // onChange={()=>{}}
                options={decimalSeparators}
                //@ts-ignore
                placeholder={Liferay.Language.get('choose-an-option')}
                readOnly={readOnly}
                showEmptyOption={false}
                value={decimalSeparator}
                visible={visible}
            />
            <Text
                //@ts-ignore
                label={Liferay.Language.get('prefix-or-suffix')}
                name="append"
                // onChange={}
                //@ts-ignore
                placeholder={Liferay.Language.get('input-mask-append-placeholder')}
                readOnly={readOnly}
                required={false}
                value={append}
                visible={visible}
            />
            <Radio
                inline={false}
                name="appendType"
                // onChange={}
                options= {[
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
                value={appendType}
            />
        </>
    );

}

export default NumericInputMask;