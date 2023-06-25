<template>
  <v-dialog 
    persistent 
    width="800" 
    scrollable
  >
    <v-card>
      <v-form v-model="formModel" ref="formRef">
        <v-card-title class="position-relative">
          <h1 class="text-h5">Comment creation</h1>
        </v-card-title>
        
        <v-divider/>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-textarea
                  v-model="commentValue"
                  :rules="[rules.required]"
                  label="Comment"
                />
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer/>
          <v-btn
            color="success"
            rounded="lg"
            variant="elevated"
            :disabled="!formModel"
            @click="submit"
          >
            Create
          </v-btn>

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
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { onMounted, ref, type VNodeRef } from 'vue';
  import { useEventBus } from '@vueuse/core';

  import { rules } from '@/helpers/rulesRegex';
  import { eventBusNames } from '@/helpers/constants';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useSnackbarStore } from '@/stores/snackbar';
  import { useCreateComment } from '@/hooks/useCreateComment';

  const busAssignment = useEventBus<string>(eventBusNames.fetchAssignment);
  const busComments = useEventBus<string>(eventBusNames.fetchComments);
  const emits = defineEmits(['close-modal']);
  
  const formRef: VNodeRef = ref(null);
  const formModel = ref(null);
  const commentValue = ref('');
  
  const { setConfirmationDataAndShow } = useConfirmationStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const {
    createComment,
    isLoading: isLoadingCreateComment,
    isSuccess: isSuccessCreateComment
  } = useCreateComment();

  const validateForm = () => {
    if (formRef.value) {
      formRef.value.validate();
    }
  };

  onMounted(async () => {
    validateForm();
  })
  
  const resetAndCloseModal = () => {
    if (formRef.value) {
      formRef.value.reset();
    }
    commentValue.value = '';
    emits('close-modal');
  };

  const submit = async () => {
    await createComment({
      text: commentValue.value
    });

    if (!isLoadingCreateComment.value && isSuccessCreateComment.value) {
      resetAndCloseModal();
      setSnackbarDataAndShow("A comment is successfully created", 'success');

      busAssignment.emit('true');
      busComments.emit('true');
    }
  };

  const closeModal = () => {
    setConfirmationDataAndShow({
      title: "Comment creation cancellation",
      content: "Do you really want to cancel the creation of the comment? " +
        "All your entered data will be lost",
      onConfirmAction: resetAndCloseModal
    });
  };
</script>