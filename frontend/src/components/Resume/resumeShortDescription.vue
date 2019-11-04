<template> 
    <div>

        <li class="my-resumes" v-for="resume in resumes" v-bind:key="resume">
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
                Brief descriprion: {{resume.brief-description}}
            </p>
        </li>
    </div>
</template>

<script>
export default {
    name: 'ResumeShortDescription',
    data(){
        return {
            resumes : [
                       {"name" : "resume1", "vacancy" : "vacancy 1", "salary" : "70000", "brief-description" : "description1"}, 
                       {"name" : "resume2", "vacancy" : "vacancy 2", "salary" : "80000", "brief-description" : "description2"}, 
                       {"name" : "resume3", "vacancy" : "vacancy 3", "salary" : "90000", "brief-description" : "description3"}
                      ]
        }
    }
}
</script>