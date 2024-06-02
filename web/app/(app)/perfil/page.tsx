'use client';

import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';

import { useUser } from '@auth0/nextjs-auth0/client';
import { zodResolver } from '@hookform/resolvers/zod';
import { PencilIcon } from 'lucide-react';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

function displayAcceptedTypes(acceptedTypes: string[]) {
  return acceptedTypes
    .join(', ')
    .replace(/(image\/)/g, '')
    .toLocaleUpperCase();
}

const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
const ACCEPTED_IMAGE_TYPES = [
  'image/jpeg',
  'image/jpg',
  'image/png',
  'image/webp',
];

const formSchema = z.object({
  avatar: z
    .any()
    .transform((files) => files.item(0) as File | null)
    .refine((file) => !!file && file.size <= MAX_FILE_SIZE, {
      message: 'A imagem da praia deve ter no máximo 5MB.',
    })
    .refine((file) => !!file && ACCEPTED_IMAGE_TYPES.includes(file.type), {
      message: `Somente os formatos ${displayAcceptedTypes(ACCEPTED_IMAGE_TYPES)} são suportados.`,
    }),
});

type FormFields = z.infer<typeof formSchema>;

export default function PerfilPage() {
  const { user } = useUser();

  const form = useForm<FormFields>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      avatar: null,
    },
  });

  function onSubmit(values: FormFields) {
    console.log(values);
  }

  return (
    <div className='m-4 flex flex-col gap-4'>
      <div className='relative'>
        <Avatar className='h-36 w-36'>
          <AvatarImage src={user?.picture || undefined} />
          <AvatarFallback>
            {user?.name?.at(1)?.toLocaleUpperCase()}
          </AvatarFallback>
        </Avatar>
        <Badge className='absolute bottom-2 left-2 text-xs'>
          <PencilIcon className='mr-1 h-3 w-3' /> Editar
        </Badge>
      </div>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className='space-y-8'>
          <FormField
            control={form.control}
            name='avatar'
            render={() => (
              <FormItem>
                <FormLabel>
                  <FormControl>
                    <Input
                      type='file'
                      accept={ACCEPTED_IMAGE_TYPES.join(', ')}
                      {...form.register('avatar')}
                    />
                  </FormControl>
                </FormLabel>
                <FormMessage />
              </FormItem>
            )}
          />

          <Button type='submit'>Salvar</Button>
        </form>
      </Form>
    </div>
  );
}
