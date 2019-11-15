<template> 
    <div>
        <ol>
            <li class="my-resumes" v-for="resume in resumes" v-bind:key="resume">
                <p>
                    Resume name: {{resume.resume_name}}
                </p>
            </li>
        </ol>
    </div>
</template>

<script>
    import axiosConfig from '../../config/axiosConfig';
    var login = "2fb92a8f-c592-4c12-bcd2-dee5a4aa3237";
    export default {
        name: 'ResumeShortDescription',
        data(){
            return {
                resumes : [],
            }
        },
        create() {
            axiosConfig.get(`${login}/resumes/`)
            .then(response => {
                // JSON responses are automatically parsed.
                alert(response.data);
                
                this.resumes = response.data;
            })
            .catch(e => {
                this.errors.push(e)
            })
        }
    }
</script>