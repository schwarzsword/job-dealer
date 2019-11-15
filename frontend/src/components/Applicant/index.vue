<template>
  <div>
    <ol>
      <li class="account" v-for="resume in resumes" v-bind:key="resume">
        <p>
          Resume name: {{resume.name}}
        </p>
        <p>
          Vacancy: {{resume.vacancy}}
        </p>
        <p>
          Salary: {{resume.salary}}
        </p>
        <p>
          Brief descriprion: {{resume.description}}
        </p>
      </li>
    </ol>
  </div>
</template>

<script>
  export default {
    name: 'Applicant',
    data() {
      return {
        resumes : [
          {name : "resume1", vacancy : "vacancy 1", salary : "70000", description : "description1"},
          {name : "resume2", vacancy : "vacancy 2", salary : "80000", description : "description2"},
          {name : "resume3", vacancy : "vacancy 3", salary : "90000", description : "description3"}
        ]
      }
    }
  }
</script>