<template>
    <div>
        <div class="left-sidebar">
            <div>
                <label>
                    <input class="people-search" name="people-search" placeholder="Search peoples" type="text">
                </label>
            </div>
            <template v-for="(item, index) in items">
                <MessageShortItem
                        :author="`${item.author}`"
                        :authorId="`${item.authorId}`"
                        :avatar="`${item.avatar}`"
                        :status="`${item.status}`"
                        :title="`${item.title}`"/>
            </template>
        </div>
        <div class="content">

            <div class="dialog">
                <div class="msg-all" id='messages'>
                    <div class="a-msg">
                        <div class="text me">
                            <b>Pavel Durov</b>:<br> Hello, World!
                            <!--              <div class="info">-->
                            <!--                <div class="send-ed"></div>-->
                            <!--                <div class="date">Time</div>-->
                            <!--              </div>-->
                        </div>
                    </div>
                </div>
                <div class="field">
              <textarea class="text" id="text" name="text" placeholder="Enter your message here...">
              </textarea>
                    <div class="audio">
                        F1
                    </div>
                    <div class="smiles">
                        F2
                    </div>
                    <button class="send-button">Send</button>
                </div>
            </div>

        </div>
        <div class="right-sidebar">
            <!--RightSidebar/-->
        </div>
    </div>
</template>

<script>
    import Router from "../../router"
    import MessageItem from "./Item";
    import MessageShortItem from "./shortItem";
    import {urlPort} from "../../tool";

    export default {
        name: 'MessageDialog',
        components: {MessageShortItem, MessageItem},
        props: [],

        data() {
            return {
                company: {},
                params: Router.currentRoute.params.opponentId,

                items: [
                    {
                        id: 1,
                        avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
                        author: 'Yan Nepomyashy',
                        authorId: '312834',
                        status: "online",
                    },
                    {
                        id: 2,
                        avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
                        author: 'Magnus Carlsen',
                        authorId: '523902',
                        status: "offline",
                    },
                    {
                        id: 3,
                        avatar: '/img/no-avatar.png',
                        author: 'Garik Gasparov',
                        authorId: '31252',
                        status: "online",
                    },
                    {
                        id: 4,
                        avatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
                        author: 'Vashe Lagrav',
                        authorId: '523421',
                        status: "online",
                    },
                    {
                        id: 5,
                        avatar: '/img/no-avatar.png',
                        author: 'Anatoly Karpov',
                        authorId: '89472',
                        status: "offline",
                    }
                ],
            }
        },
        created() {
            //console.log(this.params);

            urlPort.get('/messages', {
                params: {
                    receiverId: this.params,
                    limit: 10,
                    offset: 0,
                }
            }).then(function (response) {
                console.log(response);
            })
                .catch(function (error) {
                    console.log(error);
                })
        }
    }
</script>

<style>
    .people-search {
        display: table;
        border: 1px solid #d5d5d5;
        outline: none;
        border-radius: 3px;
        width: 100%;
        height: 30px;
        line-height: 30px;
        padding: 0 20px;
    }

    .dialog {
        display: table;
        padding: 0;
        margin: 0 auto;
        width: 460px;
        min-height: calc(100vh - 160px);
        height: 100%;
        background: #fff;
    }

    .msg-all {
        width: 100%;
        /*min-height: 100%;*/
        overflow: auto;
    }

    .msg-all .a-msg {
        display: table;
        width: calc(100% - 20px);
        margin: 10px;
        border: 0;
    }

    .msg-all .a-msg .text {
        padding: 10px 20px;
        margin: 10px;
        font-size: 14px;
        /*background: #eee;*/
        width: fit-content;
        max-width: 70%;
        min-height: fit-content;
        border-radius: 20px;
    }

    .msg-all .a-msg .you {
        float: left;
        /*background: rgba(255, 188, 118, 0.30);*/
    }

    .msg-all .a-msg .me {
        float: left;
        /*background: rgba(157, 214, 234, 0.30);*/
    }

    .msg-all .a-msg .text .info .date {
        width: fit-content;
        height: 20px;
        border-radius: 50%;
        float: left;
        margin: 0 5px;
        font-size: 12px;
        color: #a0a0a0;
    }

    .msg-all .a-msg .text .info .send {
        width: 10px;
        height: 10px;
        background: rgba(234, 58, 60, 0.64);
        border-radius: 50%;
        float: left;
        margin: 5px;
        line-height: 20px;
    }

    .msg-all .a-msg .text .info .send-ed {
        width: 10px;
        height: 10px;
        background: rgba(152, 234, 163, 0.6);
        border-radius: 50%;
        float: left;
        margin: 5px;
        line-height: 20px;
    }

    .code {
        font-family: monospace;
        font-size: 14px;
    }

    .sticker {
        width: 200px;
        height: 150px;
        background: url(https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftelegram.org%2Ffile%2F811140183%2F1%2FCUZcEgoz428%2Fece4d02f9a12a03fde&f=1) no-repeat center;
        background-size: 120px;
        float: right;
    }

    .field {
        border-top: 1px solid #f3f3f3;
        width: 460px;
        min-height: 80px;
        background: #fff;
        bottom: 0;
        position: fixed;
    }

    .field .text {
        margin: 10px;
        padding: 15px 20px;
        width: calc(100% - 100px);
        height: 60px;
        border: 1px solid #dddddd;
        border-radius: 25px;
        outline: none;
        font-size: 14px;
        line-height: 20px;
        float: left;
        box-shadow: inset 0 0 4px 0 rgba(0, 0, 0, 0.1);
    }

    .field .smiles {
        display: block;
        margin: 20px 280px;
        width: 32px;
        height: 32px;
        position: absolute;
        background: #ffe29a;
        border-radius: 50%;
        font-size: 12px;
        text-align: center;
        line-height: 32px;
        color: #909090;
    }

    .field .audio {
        display: block;
        margin: 20px 320px;
        width: 32px;
        height: 32px;
        position: absolute;
        background: #baeac2;
        border-radius: 50%;
        font-size: 12px;
        text-align: center;
        line-height: 32px;
        color: #909090;
    }

    .send-button {
        padding: 0 20px;
        margin: 21px auto;
        display: block;
        height: 38px;
        width: fit-content;
        line-height: 38px;
        border-radius: 20px;
        border: 0;
        outline: none;
        font-size: 14px;
        background-color: #5059cd;
        color: #ffffff;
        cursor: pointer;
        transition: .05s;
    }

    .send-button:hover {
        background-color: #565cea;
        text-decoration: none;
    }

    .send-button:active {
        background-color: #3e449d;
        padding: 0 20px;
        font-size: 13px;
    }
</style>
