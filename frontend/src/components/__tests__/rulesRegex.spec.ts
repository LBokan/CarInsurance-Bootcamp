import { reactive } from 'vue';
import { describe, it, expect } from 'vitest';

import { rules } from '../../helpers/rulesRegex';

describe('rulesRegex', () => {
  const reactiveRules = reactive(rules);

  it('get the right validation response for "require" rule', () => {
    expect(reactiveRules.required('')).toBe('Value is required');
    expect(reactiveRules.required('Name')).toBe(true);
    expect(reactiveRules.required('123')).toBe(true);
  });

  it('get the right validation response for "email" rule', () => {
    expect(reactiveRules.email('aaa')).toBe('Value is not a valid email address');
    expect(reactiveRules.email('test@test.com')).toBe(true);
  });

  it('get the right validation response for "password" rule', () => {
    expect(reactiveRules.password('1')).toBe('This field should be at least 8 characters long');
    expect(reactiveRules.password('12345678')).toBe(true);
    expect(reactiveRules.password('aaaaaaaa10')).toBe(true);
  });

  it('get the right validation response for "phoneNumber" rule', () => {
    expect(reactiveRules.phoneNumber('aaa')).toBe('Value is not a valid phone number');
    expect(reactiveRules.phoneNumber('123-123-123')).toBe(true);
    expect(reactiveRules.phoneNumber('+1-123-123-123')).toBe(true);
  });

  it('get the right validation response for "zipCode" rule', () => {
    expect(reactiveRules.zipCode('aaa')).toBe('Value is not a valid zip code');
    expect(reactiveRules.zipCode('12345-aaa')).toBe('Value is not a valid zip code');
    expect(reactiveRules.zipCode('12345')).toBe(true);
    expect(reactiveRules.zipCode('12345-1234')).toBe(true);
  });

  it('get the right validation response for "odometerValue" rule', () => {
    expect(reactiveRules.odometerValue('aaa')).toBe('Value is not a valid odometer value');
    expect(reactiveRules.odometerValue('100aaa')).toBe('Value is not a valid odometer value');
    expect(reactiveRules.odometerValue('-10')).toBe('Value is not a valid odometer value');
    expect(reactiveRules.odometerValue('0')).toBe('Value is not a valid odometer value');
    expect(reactiveRules.odometerValue('1')).toBe(true);
  });

  
  it('get the right validation response for "licensePlate" rule', () => {
    expect(reactiveRules.licensePlate('aaa')).toBe('Value is not a valid license plate value');
    expect(reactiveRules.licensePlate('12345')).toBe('Value is not a valid license plate value');
    expect(reactiveRules.licensePlate('1234567')).toBe(true);
    expect(reactiveRules.licensePlate('12345678')).toBe(true);
    expect(reactiveRules.licensePlate('AAaaA7A')).toBe(true);
  });

  it('get the right validation response for "vinNumber" rule', () => {
    expect(reactiveRules.vinNumber('aaa')).toBe('Invalid VIN code');
    expect(reactiveRules.vinNumber('12345')).toBe('Invalid VIN code');
    expect(reactiveRules.vinNumber('12345678901234567')).toBe(true);
    expect(reactiveRules.vinNumber('123456789ABCDEFGH')).toBe(true);
  });
});