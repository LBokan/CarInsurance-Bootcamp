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
      rounded="lg"
      required 
      clearable 
      v-model="state.email"
      :error-messages="getErrorMessage('email')" 
      @input="v$.email.$touch" 
      @blur="v$.email.$touch"
      label="Email" 
      hint="Enter your email to access the website"
    />

    <v-text-field 
      class="mb-10 w-100 rounded-lg" 
      variant="outlined"
      rounded="lg"
      required 
      clearable 
      v-model="state.password"
      :error-messages="getErrorMessage('password')" 
      :append-inner-icon="isPasswordShown ? 'mdi-eye-off' : 'mdi-eye'" 
      :type="isPasswordShown ? 'text' : 'password'"
      @input="v$.password.$touch" 
      @blur="v$.password.$touch"
      @click:append-inner="togglePasswordOnClick"
      label="Password"
      hint="Enter your password to access the website"
    />

    <v-btn 
      color="success"
      size="x-large"
      rounded="lg"
      block
      :disabled="isSubmitButtonDisabled"
      type="submit"
      variant="elevated"
    >
      LOG IN
    </v-btn>
  </v-form>
</template>

<script setup lang="ts">
  import { reactive, ref, computed } from 'vue';

  import { useValidateLoginForm } from '@/hooks/useValidateLoginForm';

  const isFormFilled = ref<boolean>(true);
  const isPasswordShown = ref<boolean>(false);

  const state = reactive({
    email: '',
    password: ''
  });

  const { v$, getErrorMessage } = useValidateLoginForm(state);
  
  const togglePasswordOnClick = () => {
    isPasswordShown.value = !isPasswordShown.value;
  };

  const isSubmitButtonDisabled = computed<boolean>(() => {
    return !state.email || !state.password || !isFormFilled.value ? 
    true :
    false;
  });

  const onSubmit = () => {
    // logic will be added
  };
</script>
