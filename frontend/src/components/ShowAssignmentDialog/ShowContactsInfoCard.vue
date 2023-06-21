<template>
  <v-col cols="12" sm="6">
    <v-window v-model="contactPage" show-arrows="hover">
      <v-window-item v-for="contact in assignment.contacts" :key="contact.id">
        <v-card>
          <h3 class="text-subtitle-1 font-weight-bold">{{ contact.type }}</h3>

          <p class="mt-5">
            <span class="text-subtitle-2">First name:</span>
            {{ contact.firstName }}
          </p>
          <p>
            <span class="text-subtitle-2">Last name:</span>
            {{ contact.lastName }}
          </p>
          <p>
            <span class="text-subtitle-2">Email:</span>
            {{ contact.email }}
          </p>

          <v-divider class="my-2" :thickness="4" />

          <p class="text-subtitle-2 font-weight-bold">Phone numbers:</p>
          <p v-for="phoneNumber in contact.phoneNumbers" :key="phoneNumber.id">
            <span class="text-subtitle-2">{{ phoneNumber.type }}:</span>
            {{ phoneNumber.number }}
          </p>

          <v-divider class="my-2" :thickness="4" />

          <p class="text-subtitle-2 font-weight-bold">Addresses:</p>
          <div v-for="address in contact.addresses" :key="address.id">
            <v-divider v-if="address.id != contact.addresses[0].id" class="my-2" />

            <p>
              <span class="text-subtitle-2">Type:</span>
              {{ address.type }}
            </p>
            <p>
              <span class="text-subtitle-2">Address line:</span>
              {{ address.addressLine }}
            </p>
            <p>
              <span class="text-subtitle-2">City, state, ZIP-code:</span>
              {{ address.city }}, {{ address.state }}, {{ address.zip }}
            </p>
          </div>
        </v-card>
      </v-window-item>
    </v-window>
  </v-col>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { storeToRefs } from 'pinia';
  import { useAssignmentStore } from '@/stores/assignment';

  const { assignment } = storeToRefs(useAssignmentStore());

  const contactPage = ref(0);
</script>