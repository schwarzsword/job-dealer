<template>
  <div class="about">
    <h1>{{title}}</h1>
    <p>{{msg}}</p>
  </div>
</template>

<script>
  export default {
    name: 'About',
    data () {
      return {
        title: 'About',
        msg: 'Text'
      }
    }
  }
</script>

<style scoped>
</style>