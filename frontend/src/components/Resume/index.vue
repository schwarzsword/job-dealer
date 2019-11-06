<template>
    <div>
        <button>
            <router-link to="/resumeForm">Create resume</router-link>
        </button>
            
        <ResumeShortDescription></ResumeShortDescription>
    </div>
    
</template>

<script>
    export default {
        name: 'Resume'
    }
</script>