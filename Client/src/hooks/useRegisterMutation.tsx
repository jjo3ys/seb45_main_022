import { useMutation } from '@tanstack/react-query';
import { registerAuth } from '../api/auth';

export default function useRegisterMutation() {
  return useMutation(registerAuth);
}
