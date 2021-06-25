import React from 'react';
declare type DecimalSeparator = ',' | '.';
declare type ThousandsSeparator = DecimalSeparator | ' ' | "'" | 'none';
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
	checked: boolean;
	label: string;
	value: T;
}
declare const NumericInputMask: React.FC<IProps>;
export default NumericInputMask;
