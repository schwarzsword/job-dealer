<template>
  <div class="item">
    <v-card>
      <v-card-text>
        <router-link :to="/resumes/ + `${id}`" class="title">{{ title }}</router-link>
        <div class="author">{{ author }}</div>
        <!--        <div class="desc">-->
        <!--          {{ subtitle }}-->
        <!--        </div>-->
        <div class="item-bottom">
          <router-link class="invite" to="">Send invite</router-link>
          <div class="date">25 december</div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
  export default {
    name: 'ResumeItem',
    props: ['id', 'title', 'author', 'subtitle'],

    data() {
      return {}
    },
    created() {
    }
  }
</script>
