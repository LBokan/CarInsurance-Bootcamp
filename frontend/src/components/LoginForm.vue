<template>
  <v-form 
    class="d-flex flex-column justify-center align-center"
    v-model="isFormFilled"
    @submit.prevent="onSubmit"
  >
    <p class="mb-10 text-subtitle-1">Log in to proceed</p>

    <v-text-field 
      class="mb-5 w-100" 
      variant="outlined"
      :color="isEmailInputWithError"
      :focused="!!isEmailInputWithError"
      rounded="lg"
      required 
      clearable 
      v-model="credentials.email"
      :rules="[rules.required, rules.email]"
      label="Email" 
      hint="Enter your email to access the website"
    />

    <v-text-field 
      class="mb-10 w-100 rounded-lg" 
      variant="outlined"
      :color="isPasswordInputWithError"
      :focused="!!isPasswordInputWithError"
      rounded="lg"
      required
      clearable
      v-model="credentials.password"
      :rules="[rules.required, rules.password]"
      :append-inner-icon="isPasswordShown ? 'mdi-eye-off' : 'mdi-eye'" 
      :type="isPasswordShown ? 'text' : 'password'"
      @click:append-inner="togglePasswordOnClick"
      label="Password"
      hint="Enter your password to access the website"
    />

    <v-btn 
      color="success"
      size="x-large"
      rounded="lg"
      :loading="isLoading"
      block
      :disabled="isSubmitButtonDisabled"
      type="submit"
      variant="elevated"
    >
      log in
    </v-btn>
  </v-form>
</template>

<script setup lang="ts">
  import { reactive, ref, computed } from 'vue';
  import { useRouter } from 'vue-router';

  import { useLogin } from '@/hooks/useLogin';

  const router = useRouter();

  const { 
    credentials,
    loginFetch,
    isLoading,
    isError,
    errorData,
    isSuccess
  } = useLogin();

  const isFormFilled = ref<boolean>(true);
  const isPasswordShown = ref<boolean>(false);

  type TypeValidateFunc = (value: string) => boolean | string;

  interface ILoginRules {
    [n: string]: TypeValidateFunc
  };

  const rules: ILoginRules = reactive({
    required: (value) => value ? true : 'Value is required',
    email: (value) => /.+@.+\..+/.test(value) ? 
      true : 
      'Value is not a valid email address',
    password: (value) => (value && value.length >= 8) ? 
      true : 
      'This field should be at least 8 characters long'
  });

  const isEmailInputWithError = computed<string | undefined>(() => {
    return isError.value && errorData.value.includes("email") ? 'error' : undefined;
  });

  const isPasswordInputWithError = computed<string | undefined>(() => {
    return isError.value && errorData.value.includes("password") ? 'error' : undefined;
  });

  const togglePasswordOnClick = () => {
    isPasswordShown.value = !isPasswordShown.value;
  };

  const isSubmitButtonDisabled = computed<boolean>(() => {
    return !credentials.email || !credentials.password || !isFormFilled.value ? 
    true :
    false;
  });

  const onSubmit = async () => {
    await loginFetch();

    if (isSuccess.value && !isError.value) {
      router.push("/");
    }
  };
</script>
