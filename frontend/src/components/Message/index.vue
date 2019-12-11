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
                <template v-for="(item, index) in items">
                    <MessageItem
                            :author="`${item.author}`"
                            :authorId="`${item.authorId}`"
                            :avatar="`${item.avatar}`"
                            :subtitle="`${item.subtitle}`"
                            :title="`${item.title}`"/>
                </template>
            </div>
        </div>
        <div class="right-sidebar">
            <!--RightSidebar/-->
        </div>
    </div>
    <div class="content">
      <div class="window">
        <template v-for="(item, index) in items">
          <MessageItem
              :author="`${item.author}`"
              :authorId="`${item.authorId}`"
              :avatar="`${item.avatar}`"
              :subtitle="`${item.subtitle}`"
              :title="`${item.title}`"/>
        </template>
      </div>
    </div>
    <div class="right-sidebar">
      <!--RightSidebar/-->
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  import MessageItem from "./Item";

  export default {
    name: 'Message',
    components: {MessageItem},
    data() {
      return {
        companies: [],
        items: [
          {
            id: 1,
            avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
            author: 'Yan Nepomyashy',
            authorId: '312834',
            subtitle: "I'll be in your neighborhood doing errands this weekend. Do you want to hang out?"
          },
          {
            id: 2,
            avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
            author: 'Magnus Carlsen',
            authorId: '523902',
            subtitle: "Wish I could come, but I'm out of town this weekend."
          },
          {
            id: 3,
            avatar: '/img/no-avatar.png',
            author: 'Garik Gasparov',
            authorId: '31252',
            subtitle: "Do you have Paris recommendations? Have you ever been?"
          },
          {
            id: 4,
            avatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
            author: 'Vashe Lagrav',
            authorId: '523421',
            subtitle: "Have any ideas about what we should get Heidi for her birthday?"
          },
          {
            id: 5,
            avatar: '/img/no-avatar.png',
            author: 'Anatoly Karpov',
            authorId: '89472',
            subtitle: "We should eat this: Grate, Squash, Corn, and tomatillo Tacos."
          }
        ]
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

  .messages .msg {
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
    width: calc(100% - 35px);
    margin: 5px 5px 15px 25px;
    padding: 5px 15px;
    border-radius: 20px;
    background-color: #8BC34A;
    color: #fff;
    font-size: 14px;
    text-align: center;
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
    border: 1px solid #f0f0f0;
  }

  .left-sidebar .actions .action {
    display: table;
    padding: 0 10px;
    height: 30px;
    width: 100%;
    line-height: 30px;
  }

  .left-sidebar .actions .action:hover {
    background-color: #ffff;
  }

  .left-sidebar .actions .active {
    background-color: #f0f0f0;
    color: #000;
  }

  .left-sidebar .actions .active:hover {
    background-color: #f0f0f0;
    color: #000;
  }

  /* content */
  .content .window {
    display: table;
    margin: 5px 10px 5px 10px;
    width: calc(100% - 20px);
    min-height: 400px;
    border: 1px solid #f3f3f3;
  }

  .content .window .header {
  }

  .content .window .dialogs {
  }

  .content .window .sub-dialog {
  }
</style>
