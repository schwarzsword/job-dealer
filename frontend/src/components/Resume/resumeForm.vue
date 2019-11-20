<template>
    <div>
        
        Название резюме: <input type="text" name="resumeName"> <br>
        Фамилия: <input type="text" name="lastName"> <br>
        Имя: <input type="text" name="firstName"> <br>
        Отчество: <input type="text" name="middleName"> <br>
        Должность: <input type="text" name="vacancy"> <br>
        Ожидаемый оклад: <input type="text" name="salary"> <br>
        О себе: <input type="text" name="description"> <br>
        <button>Принять</button>
    </div>
</template>

<script>
import ResumeShortDescription from './resumeShortDescription'
export default {
    name: 'ResumeForm',
    data(){
        return {
            a: "aaa",
        }
    },
    mounted: function() {
        ResumeShortDescription.create();
    }
}
</script>