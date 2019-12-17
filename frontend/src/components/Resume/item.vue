<template>
    <div class="item">
        <v-card>
            <v-card-text>
                <router-link :to="/resumes/ + `${id}`" class="title">{{ title }}</router-link>
                <div class="author">{{ author }}</div>
                <div class="item-bottom">
                    <!--router-link class="invite" to="">Send invite</router-link>-->
                    <div class="date">{{ rDate }} december</div>
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
            return {
                rDate: 0
            }
        },
        created() {
            this.rDate = Math.floor((Math.random() * 10) + 1);
        }
    }
</script>
