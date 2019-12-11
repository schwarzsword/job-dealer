<template>

    <div v-if="reviews">
        <div v-if="profile.id !== accountId">
            <div v-if="yourReview.id">
                <h3>Your review</h3>
                <ReviewItem
                        :account-id="profile.id"
                        :id="yourReview.id"
                        :lowest-rating="lowestRating"
                        :rating="yourReview.rating"
                        :sender="yourReview.sender"
                        :text="yourReview.text"
                        :voted="yourReview.voted"
                />
            </div>
            <div v-else>
                <v-textarea
                        label="Write your review here"
                        solo
                        v-model="yourReview.text"
                />
                <v-btn @click="submit">Send</v-btn>
            </div>
        </div>
        <v-card class="reviews">
            <div>Reviews</div>
            <v-text-field
                    label="Hide reviews with rating lower than:"
                    type="number"
                    v-model="lowestRating"/>
            <hr>
            <div v-for="item in this.reviews">
                <ReviewItem
                        :account-id="profile.id"
                        :id="item.id"
                        :lowest-rating="lowestRating"
                        :rating="item.rating"
                        :sender="item.sender"
                        :text="item.text"
                        :voted="item.voted"
                />
            </div>
        </v-card>
    </div>
</template>

<script>
  import {urlPort} from "../../tool";
  import ReviewItem from "./ReviewItem";
  import {mapGetters} from 'vuex'


    export default {
        name: "ReviewList",
        components: {ReviewItem},
        props: ["accountId"],
        data() {
            return {
                reviews: [],
                lowestRating: -10,
                yourReview: {
                    id: null,
                    text: null,
                    rating: null,
                    sender: null,
                    voted: null,
                }
            }
        },
        created() {
            urlPort.get("/accounts/" + this.accountId + "/reviews")
                .then(resp => {
                    this.reviews = resp.data;
                });

            urlPort.get("/accounts/" + this.accountId + "/reviews/my")
                .then(resp => {
                    this.yourReview = resp.data;
                })
        },
        computed: {
            ...mapGetters(['profile']),
        },
        methods: {
            submit() {
                let params = new URLSearchParams();
                params.append('text', this.yourReview.text);
                urlPort.post("/accounts/" + this.accountId + "/reviews", params)
                    .then(resp => {
                        this.yourReview = resp.data;
                    });
            }
        }
    }
  }
</script>

<style scoped>
  .content .item {
    display: block;
    width: calc(100% - 20px);
    height: auto;
    padding: 20px;
    margin: 0 10px;
    background-color: #fafafa;
    border-bottom: 1px solid #f0f0f0;
  }
</style>