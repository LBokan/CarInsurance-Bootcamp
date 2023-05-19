import { useVuelidate } from '@vuelidate/core';
import { email, required, minLength } from '@vuelidate/validators';
import { computed } from 'vue';

import type { ComputedRef, Ref } from 'vue';
import type { 
  ErrorObject,
  Validation, 
  ValidationRuleWithoutParams, 
  ValidationRuleWithParams 
} from '@vuelidate/core';

interface ILoginState {
  email: string,
  password: string
}

interface ILoginRules {
  email: {
    required: ValidationRuleWithoutParams<any>,
    email: ValidationRuleWithoutParams<any>
  },
  password: {
      required: ValidationRuleWithoutParams<any>,
      minLength: ValidationRuleWithParams< { min: number }, any >
  };
}

type TypeGetErrorMessage = ( stateVal: string ) => string[];

interface IReturnObj {
  v$: Ref<Validation<ILoginRules, ILoginState>>,
  getErrorMessage: ComputedRef<TypeGetErrorMessage>
}

export function useValidateLoginForm( state: ILoginState ): IReturnObj {
  const rules: ILoginRules = {
    email: {
        required, email
    },
    password: {
        required, minLength: minLength(8)
    }
  };

  const v$ = useVuelidate( rules, state );

  const getErrorMessage = computed<TypeGetErrorMessage>(() => {
    return (stateVal: string) => {
        return v$?.value?.[stateVal]?.$error ? 
        (v$?.value?.[stateVal]?.$errors.map((e: ErrorObject) => e.$message) as string[]) :
        [];
    };
  });

  return { v$, getErrorMessage };
};