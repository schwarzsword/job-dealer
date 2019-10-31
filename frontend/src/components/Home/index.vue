<template>
  <div class="home">
    <h1>{{title}}</h1>
    <p>{{msg}}</p>
  </div>
</template>

<script>
  export default {
    name: "Home",
    data () {
      return {
        title: 'Home',
        msg: 'Welcome to Job Dealer!'
      }
    }
  }
</script>

<style scoped>
</style>