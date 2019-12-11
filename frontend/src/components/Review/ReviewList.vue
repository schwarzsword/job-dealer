<template>
  <div v-if="reviews">
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
        can: null,
        lowestRating: -10,
      }
    },
    created() {
      urlPort.get("/accounts/" + this.accountId + "/reviews")
        .then(resp => {
          this.reviews = resp.data;
        })
    },
    computed: {
      ...mapGetters(['profile']),
    },
    methods: {
      canVote(item) {
        urlPort.get("/reviews/" + item.id)
          .then(resp => {
            this.can = resp.data;
          })
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