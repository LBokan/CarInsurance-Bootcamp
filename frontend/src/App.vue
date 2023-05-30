<template>
  <v-layout class="bg-blue-lighten-1">
    <v-main class="app">
      <router-view />
    </v-main>

    <NotificationBar />
  </v-layout>
</template>

<script setup lang="ts">
  import NotificationBar from "@/components/NotificationBar.vue";
</script>

<style>
  .app {
    display: flex;
    flex-direction: column;
    height: 100dvh;
  }
</style>