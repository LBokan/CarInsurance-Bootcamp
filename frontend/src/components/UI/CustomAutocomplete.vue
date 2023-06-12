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
    :label="newLabelName"
    :hint="hint"
    :error="error"
    :error-messages="errorMessages"
  />
</template>

<script setup lang="ts">
  import { defineProps, ref } from 'vue';

  const props = defineProps({
    classes: String,
    color: String,
    isRequired: Boolean,
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

  const newLabelName = ref(props.isRequired ? `${props.label}*` : props.label);
</script>