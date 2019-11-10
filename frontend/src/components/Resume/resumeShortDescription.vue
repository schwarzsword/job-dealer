<template> 
    <div>
        <ol>
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
                    Brief descriprion: {{resume.description}}
                </p>
            </li>
        </ol>
    </div>
</template>

<script>
export default {
    name: 'ResumeShortDescription',
    data(){
        return {
            resumes : [],
        }
    },
    created() {
        axios.get(`http://localhost:8080/api/user/${login}/resumes/`)
        .then(response => {
            // JSON responses are automatically parsed.
            this.resumes = response.data;
        })
        .catch(e => {
            this.errors.push(e)
        })
    }
}
</script>