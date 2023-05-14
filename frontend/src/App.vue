<template>
  <h1 class="test">
    Hello, {{ text }}!
  </h1>
</template>

<script>
export default {
  data() {
    return {
      text: 'world'
    }
  }
}
</script>

<style>
.test {
  color: red;
}
</style>
