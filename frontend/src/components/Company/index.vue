<template>
  <div>
    <div class="companies">
      <div class="company" v-for="company in companies" v-bind:key="company">
        <div class="title">
          <h2><a href="/">{{company.name}}</a></h2>
        </div>
        <div class="description">{{company.description}}</div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Company',
    data() {
      return {
        companies : [
          {name : "Vkontakte LLC.", description : "description1"},
          {name : "Yandex Ltd.", description : "description2"},
          {name : "Google Inc.", description : "description3"}
        ]
      }
    }
  }
</script>

<style>
  .companies {
    display: table;
    width: 100%;
  }
  .companies .company {
    margin: 0 0 10px 0;
    padding: 10px;
    display: table;
    width: 100%;
    height: auto;
    border: 1px solid #eee;
  }
  .companies .company .title h2 {
    font-size: 16px;
  }
  .companies .company .title a {

  }
</style>