<template>
    <div>

        Название резюме: <input name="resumeName" type="text"> <br>
        Фамилия: <input name="lastName" type="text"> <br>
        Имя: <input name="firstName" type="text"> <br>
        Отчество: <input name="middleName" type="text"> <br>
        Должность: <input name="vacancy" type="text"> <br>
        Ожидаемый оклад: <input name="salary" type="text"> <br>
        О себе: <input name="description" type="text"> <br>
        <button>Принять</button>
    </div>
</template>

<script>
    import ResumeShortDescription from './resumeShortDescription'

    export default {
        name: 'ResumeForm',
        data() {
            return {
                a: "aaa",
            }
        },
        mounted: function () {
            ResumeShortDescription.create();
        }
    }
</script>