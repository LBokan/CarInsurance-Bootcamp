<template>
  <v-layout class="bg-blue-lighten-1">
    <v-main class="app">
      <router-view />
    </v-main>

    <NotificationBar />
    <ConfirmationModal />
  </v-layout>
</template>

<script setup lang="ts">
  import NotificationBar from '@/components/NotificationBar.vue';
  import ConfirmationModal from '@/components/ConfirmationModal.vue';
</script>

<style>
  .app {
    display: flex;
    flex-direction: column;
    height: 100dvh;
  }
</style>