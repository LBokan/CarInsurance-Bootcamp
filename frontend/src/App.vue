<template>
  <v-layout class="bg-blue-lighten-1">
    <v-main class="app">
      <router-view />
    </v-main>
  </v-layout>
</template>

<style>
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  .app {
    display: flex;
    flex-direction: column;
    height: 100vh;
  }
</style>