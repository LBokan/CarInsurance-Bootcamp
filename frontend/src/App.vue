<template>
  <v-layout class="bg-blue-lighten-1">
    <v-main class="app">
      <router-view />
    </v-main>
  </v-layout>
</template>

<style>
  .app {
    display: flex;
    flex-direction: column;
    height: 100dvh;
  }
</style>