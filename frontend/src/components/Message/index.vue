<template>
  <div>
    <div class="left-sidebar">
      <router-link class="write" to="/">Write message</router-link>
      <div class="actions">
        <router-link class="action active" to="/">Inbox</router-link>
        <router-link class="action" to="/">Sent</router-link>
        <router-link class="action" to="/">Archive</router-link>
        <router-link class="action" to="/">Spam</router-link>
        <router-link class="action" to="/">Deleted</router-link>
      </div>
    </div>
    <div class="content">
      <div class="window">
        ...
      </div>
    </div>
    <div class="right-sidebar">
      <RightSidebar/>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  import {urlPort} from "../../tool";

  export default {
    name: 'Message',

    data() {
      return {
        companies: [],
      }
    },
    created() {
      axios.get("http://127.0.0.1:8080/messages")
        .then(response => {
          this.companies = response.data;
        })
        .catch(e => {
          this.errors.push(e)
        })
    }
  }
</script>

<style>
  .messages {
    display: table;
    width: 100%;
  }

  .messages .message {
    margin: 10px;
    padding: 10px;
    display: table;
    width: 480px;
    height: 100px;
    border: 1px solid #eee;
    background: #fff;
    box-shadow: 0 0 1px 0 rgba(0, 0, 0, .2);
    border-radius: 6px;
  }

  .messages .message .title h2 {
    font-size: 20px;
  }

  .messages .message .title a {
  }

  .left-sidebar .write {
    display: table;
    width: calc(100% - 75px);
    margin: 5px 10px 15px 25px;
    padding: 5px 20px;
    border-radius: 20px;
    background-color: #8BC34A;
    color: #fff;
  }
  .left-sidebar .write:hover {
    background-color: #96d350;
  }
  .left-sidebar .write:active {
    background-color: #79aa41;
  }

  .left-sidebar .actions {
    display: table;
    width: calc(100% - 27px);
    margin: 5px 5px 5px 20px;
    border-radius: 5px;
    border: 1px solid #f3f3f3;
  }
  .left-sidebar .actions .action {
    display: table;
    padding: 0 10px;
    height: 30px;
    width: calc(100% - 20px);
    line-height: 30px;
  }
  .left-sidebar .actions .active {
    background-color: #f3f3f3;
    color: #000;
  }
  .left-sidebar .actions .action:hover {
    background-color: #f8f8f8;
  }

  /* content */
  .content .window {
    display: table;
    margin: 5px 10px 5px 10px;
    width: calc(100% - 20px);
    min-height: 400px;
    border: 1px solid #f3f3f3;
  }
  .content .window .header {}
  .content .window .dialogs {}
  .content .window .sub-dialog {}
</style>
