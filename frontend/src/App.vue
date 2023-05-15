<template>
  <h1 class="test">
    Hello, {{ text }}!
  </h1>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const text = ref('world');
</script>

<style>
.test {
  color: red;
}
</style>
