<template>
  <v-text-field
    variant="outlined"
    rounded="lg"
    type="text"
    :class="classes" 
    :color="color"
    :required="isRequired"
    :clearable="isClearable"
    :counter="counter"
    :label="label"
    :suffix="suffix"
    :placeholder="placeholder"
    :hint="hint"
  />
</template>


<script setup lang="ts">
  import { defineProps } from 'vue';

  defineProps({
    classes: String,
    color: String,
    isRequired: Boolean,
    isClearable: Boolean,
    label: String,
    suffix: String,
    placeholder: {
      type: String,
      default: ''
    },
    hint: {
      type: String,
      default: ''
    },
    counter: {
      type: [ String, Number ]
    }
  });
</script>