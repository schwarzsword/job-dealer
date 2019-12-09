<template>
        <v-card v-if="rating>=lowestRating">
            <div class="heading">{{sender}}</div>
            <hr/>
            <div class="body-1">
                {{text}}
            </div>
            <div class="v-data-footer">
                <v-icon v-if="showArrows" color="green" @click="up">mdi-chevron-double-up</v-icon>
                {{ratingLocal}}
                <v-icon v-if="showArrows" color="red" @click="down">mdi-chevron-double-down</v-icon>
            </div>
        </v-card>
</template>

<script>
    import {urlPort} from "../../tool";

    export default {
        name: "ReviewItem",
        props: ["id", "text", "sender", "rating", "voted", "accountId", "lowestRating"],
        data() {
            return{
                ratingLocal : this.rating,
                showArrows: true,
            }
        },
        created(){
              this.showArrows = !this.voted.includes(this.accountId);
        },
        methods: {
            up() {
                urlPort.patch("/reviews/" + this.id + "/rating/increase")
                    .then(resp => {
                        this.ratingLocal++;
                        this.showArrows = false;
                    })
            },
            down() {
                urlPort.patch("/reviews/" + this.id + "/rating/decrease")
                    .then(resp => {
                        this.ratingLocal--;
                        this.showArrows = false;
                    })
            }
        },
    }
</script>

<style scoped>
    .content .item {
        display: block;
        width: calc(100% - 20px);
        height: auto;
        padding: 0;
        margin: 0 10px;
        background-color: #fafafa;
        border-bottom: 1px solid #f0f0f0;
    }
</style>