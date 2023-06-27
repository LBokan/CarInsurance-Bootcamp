<template>
  <v-app-bar color="blue-darken-4">
    <v-app-bar-title>
      <v-icon
        start 
        icon="mdi-shield-car" 
        aria-label="CarInsurance logo"
      />
      CarInsurance
    </v-app-bar-title>

    <v-list-item
      class="text-end"
      lines="two"
      :title="getUserTitle"
      :subtitle="getUserSubtitle"
    />

    <v-app-bar-nav-icon id="menu-activator" />
  </v-app-bar>

  <v-menu activator="#menu-activator">
    <v-list>
      <v-list-item
        value="1"
        prepend-icon="mdi-account"
        title="User information"
        @click="openUserInfoModal"
      />
      <v-list-item
        value="2"
        prepend-icon="mdi-logout"
        title="Logout"
        @click="openLogoutModal"
      />
    </v-list>
  </v-menu>

  <UserInfoModal 
    v-if="isUserInfoModalOpen"
    v-model="isUserInfoModalOpen"
    @close-modal="closeUserInfoModal"
  />
</template>

<script setup lang="ts">
  import { computed, onMounted, ref } from 'vue';
  import { storeToRefs } from 'pinia';

  import { ROLES } from '@/helpers/constants';
  import { logout } from '@/helpers/authorization';
  import { useUserStore } from '@/stores/user';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useUser } from '@/hooks/useGetUser';

  import UserInfoModal from '@/components/UserInfoModal.vue';

  const isUserInfoModalOpen = ref(false);

  const { userState, userRole } = storeToRefs(useUserStore());
  const { getUser } = useUser();
  const { setConfirmationDataAndShow } = useConfirmationStore();

  onMounted(async() => {
    if (!userState.value.id) {
      await getUser();
    }
  })

  const getUserTitle = computed(() => {
    return `${userState.value.firstName} ${userState.value.lastName}`
  });

  const getUserSubtitle = computed(() => {
    if (userRole.value !== ROLES.client) {
      return `Company: ${userState.value.companyOfWork}`
    }

    return '';
  });

  const openUserInfoModal = () => {
    isUserInfoModalOpen.value = true;
  };

  const closeUserInfoModal = () => {
    isUserInfoModalOpen.value = false;
  };

  const openLogoutModal = () => {
    setConfirmationDataAndShow({
      title: "Logout",
      content: "Do you really want to log out from the application?",
      onConfirmAction: logout
    });
  };
</script>