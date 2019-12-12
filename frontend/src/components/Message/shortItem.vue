<template>
    <router-link :to="'/my/messages/' + `${authorId}`" class="msg">
        <div class="m-left">
            <img :src="`${avatar}`" alt="avatar">
        </div>
        <div class="m-right">
            <div class="author">{{ author }}</div>
            <div class="status">{{ status }}</div>
        </div>
    </router-link>
</template>

<script>
  import Router from "../../router"

  export default {
        name: 'MessageShortItem',
        props: ['id', 'title', 'author', 'authorId', 'status', 'avatar'],

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
    .msg {
        display: table;
        padding: 4px 0;
        height: 50px;
        width: 100%;
        border-bottom: 1px solid #f0f0f0;
    }

    .msg:hover {
        background-color: #f3f3f3;
    }

    .msg .m-left {
        display: block;
        float: left;
        width: 48px;
    }

    .msg .m-left img {
        border-radius: 50%;
        width: 32px;
        height: 32px;
        margin: 4px 8px;
    }

    .msg .m-right {
        display: block;
        float: left;
        width: calc(100% - 48px);
    }

    .msg .author {
        display: table;
        padding: 2px 10px;
        font-size: 16px;
        color: #000;
    }

    .msg .status {
        display: table;
        padding: 2px 10px;
        font-size: 10px;
        color: #4e555b;
    }
</style>
