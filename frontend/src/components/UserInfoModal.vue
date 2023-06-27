<template>
  <v-dialog 
    persistent 
    width="800" 
    scrollable
  >
    <v-card>
      <v-card-title>
        <h1 class="text-h5">User information</h1>
      </v-card-title>
      
      <v-divider/>

      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <p class="text-body-1">
                <span class="font-weight-bold">Name:</span> {{ userState.firstName }}
              </p>
              <p class="mt-1 text-body-1">
                <span class="font-weight-bold">Surname:</span> {{ userState.lastName }}
              </p>
            </v-col>
            <v-divider />
            
            <v-col cols="12">
              <p class="mt-1 text-body-1">
                <v-icon
                  start 
                  icon="mdi-email" 
                  aria-label="Email icon"
                />
                <span class="font-weight-bold">Email:</span> {{ userState.email }}
              </p>
            </v-col>

            <v-col cols="12">
              <p v-if="userRole === ROLES.client" class="text-body-1">
                <span class="font-weight-bold">Insurance company:</span> {{ userState.insuranceCompany }}
              </p>

              <p v-if="userRole !== ROLES.client" class="text-body-1">
                <v-icon
                  start 
                  icon="mdi-domain" 
                  aria-label="Company icon"
                />
                <span class="font-weight-bold">Company of work:</span> {{ userState.companyOfWork }}
              </p>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>

      <v-card-actions>
        <v-spacer/>

        <v-btn
          class="ml-5"
          color="error"
          rounded="lg"
          variant="elevated"
          @click="closeModal"
        >
          Close
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { storeToRefs } from 'pinia';
  import { ROLES } from '@/helpers/constants';
  import { useUserStore } from '@/stores/user';

  const { userState, userRole } = storeToRefs(useUserStore());

  const emits = defineEmits(['close-modal']);

  const closeModal = () => {
    emits('close-modal');
  };
</script>