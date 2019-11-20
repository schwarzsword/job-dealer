<template>
  <div class="sidebar">
    <div class="county">
      <div class="title">
        Country:
      </div>
      <label>
        <input name="country" placeholder="Select country">
      </label>
    </div>
    <div class="salary">
      <div class="title">
        Salary:
      </div>
      <label>
        <input name="from" placeholder="From">
      </label>
      <label>
        <input name="to" placeholder="To">
      </label>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'RightSidebar',
    data() {
      return {
        companies: [],
      }
    }
  }
</script>

<style>
  .sidebar {
    display: block;
    width: 280px;
    min-height: 400px;
    margin: 0 10px;
    background-color: #f1eeee;
    padding: 10px;
    border-radius: 5px;
  }
  .sidebar .title {
    font-size: 14px;
    color: #404040;
    padding: 5px;
  }

  .sidebar .county {}
  .sidebar .county input {
    display: table;
    width: 260px;
    height: 30px;
    padding: 0 10px;
    margin-right: 10px;
    border: 1px solid #beb8b9;
    border-radius: 5px;
    outline: none;
  }

  .sidebar .salary {}
  .sidebar .salary input {
    display: table;
    float: left;
    width: 100px;
    height: 30px;
    padding: 0 10px;
    margin-right: 10px;
    border: 1px solid #beb8b9;
    border-radius: 5px;
    outline: none;
  }
</style>
