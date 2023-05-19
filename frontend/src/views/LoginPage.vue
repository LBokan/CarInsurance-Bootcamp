<template>
  <v-container class="d-flex flex-column justify-center align-center flex-grow-1">
    <v-sheet class="d-flex justify-center align-center mb-10 text-h2 font-weight-medium bg-transparent">
      <v-icon 
        class="ma-2" 
        size="x-small" 
        start 
        icon="mdi-shield-car" 
        aria-label="CarInsurance logo"
      />
      CarInsurance
    </v-sheet>

    <v-card class="m-auto px-10 py-13" width="600" rounded="xl">
      <LoginForm />
    </v-card>
  </v-container>
</template>

<script setup lang="ts">
  import LoginForm from '@/components/LoginForm.vue';
</script>