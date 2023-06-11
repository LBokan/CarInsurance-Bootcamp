<template>
  <v-autocomplete
    variant="outlined"
    rounded="lg"
    :class="classes" 
    :color="color"
    :multiple="isMultiple"
    :clearable="isClearable"
    :items="items"
    :chips="isChips"
    closable-chips
    :label="label"
    :hint="hint"
    :error="error"
    :error-messages="errorMessages"
  />
</template>

<script setup lang="ts">
  import { defineProps } from 'vue';

  defineProps({
    classes: String,
    color: String,
    isMultiple: Boolean,
    isClearable: Boolean,
    items: {
      type: Array,
      default: () => []
    },
    isChips: Boolean,
    label: String,
    hint: {
      type: String,
      default: ''
    },
    error: {
      type: Boolean,
      default: false
    },
    errorMessages: {
      type: [Array as () => string[], String],
      default: ''
    }
  });
</script>