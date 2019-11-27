<template>
  <router-link class="message" to="/">
    <div class="title">Message</div>
    <div class="author">Company</div>
    <div class="desc">
      Desc
    </div>
  </router-link>
</template>

<script>
  import Router from "../../router"

  export default {
  name: 'MessageItem',

  data() {
    return {
      company: {},
      id: Router.currentRoute.params.id,
    }
  },
  created() {
  }
}
</script>

<style>
  .message {
    display: table;
    padding: 4px 0;
    height: 50px;
    width: 100%;
    border-bottom: 1px solid #f0f0f0;
  }
  .message:hover {
    background-color: #f3f3f3;
  }
  .message .title {
    display: table;
    padding: 2px 10px;
    font-size: 16px;
  }
  .message .author {
    display: table;
    padding: 2px 10px;
    font-size: 12px;
    color: #4e555b;
  }
  .message .desc {
    display: table;
    padding: 2px 10px;
    font-size: 14px;
    color: #1b1e21;
  }
</style>
