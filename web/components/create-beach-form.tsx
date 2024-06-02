'use client';

import { saveBeach } from '@/lib/client-queries';
import { Position } from '@/lib/types';
import { displayAcceptedTypes, getApiError } from '@/lib/utils';

import { uploadBeachImage } from '@/app/(app)/praias/actions';
import { useUser } from '@auth0/nextjs-auth0/client';
import { zodResolver } from '@hookform/resolvers/zod';
import { useMutation } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import { useEffect, PropsWithChildren } from 'react';
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';
import { z } from 'zod';

import { Coordinate } from './maps/coordinate';
import { Button } from './ui/button';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from './ui/dialog';
import DragAndDrop from './ui/drag-and-drop';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from './ui/form';
import { Input } from './ui/input';
import { ScrollArea } from './ui/scroll-area';
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from './ui/select';

export interface BeachData extends Coordinate {
  name?: string;
  city: string;
  country: string;
  countryCode: string;
}

interface CreateBeachFormProps extends PropsWithChildren {
  data?: BeachData;
}

const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
const ACCEPTED_IMAGE_TYPES = [
  'image/jpeg',
  'image/jpg',
  'image/png',
  'image/webp',
];

const LAT_MATCH = /^(-?[1-8]?\d(?:\.\d{1,})?|90(?:\.0+)?)$/;
const LNG_MATCH = /^(-?(?:1[0-7]|[1-9])?\d(?:\.\d{1,})?|180(?:\.0+)?)$/;
const COUNTRY_CODE_MATCH = /^[A-Z]{2}$/;

const formSchema = z.object({
  image: z
    .any()
    .transform((files) =>
      files && files?.length
        ? (files.item(0) as File)
        : files
          ? (files as File)
          : null
    )
    .refine((file) => (file ? file.size <= MAX_FILE_SIZE : true), {
      message: 'A imagem da praia deve ter no máximo 5MB.',
    })
    .refine(
      (file) => (file ? ACCEPTED_IMAGE_TYPES.includes(file.type) : true),
      {
        message: `Somente os formatos ${displayAcceptedTypes(ACCEPTED_IMAGE_TYPES)} são suportados.`,
      }
    ),
  name: z.string().max(50).min(1),
  lat: z.number().refine((value) => LAT_MATCH.test(String(value))),
  lng: z.number().refine((value) => LNG_MATCH.test(String(value))),
  position: z.nativeEnum(Position),
  countryCode: z.string().length(2).regex(COUNTRY_CODE_MATCH),
  country: z.string(),
  city: z.string(),
});

type FormFields = z.infer<typeof formSchema>;

export default function CreateBeachForm({
  data,
  children,
}: CreateBeachFormProps) {
  const { user } = useUser();
  const router = useRouter();

  const form = useForm<FormFields>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      image: null,
      name: '',
      countryCode: '',
      city: '',
      country: '',
    },
  });

  function createFileName(beachName: string, image: File) {
    return `${user?.sub?.replace('auth0|', '')}_${beachName.toLowerCase()}.${image.type.replace('image/', '')}`;
  }

  async function uploadImage(fileName: string, file: File) {
    const formData = new FormData();
    formData.append('fileName', fileName);
    formData.append('file', file);
    return await uploadBeachImage(formData);
  }

  const { mutateAsync: saveBeachMutation, isPending } = useMutation({
    async mutationFn(data: FormFields) {
      const { city, country, countryCode, lat, lng, name, position, image } =
        data;

      const fileName = image ? createFileName(name, image) : undefined;

      try {
        const imageUrl =
          fileName && image ? await uploadImage(fileName, image) : undefined;

        await saveBeach({
          city,
          country,
          countryCode,
          lat,
          lng,
          name,
          position,
          ...(imageUrl && { image: imageUrl }),
        });
      } catch (error) {
        throw error;
      }
    },
  });

  async function onSubmit(fields: FormFields) {
    toast.promise(saveBeachMutation(fields), {
      loading: `Criando ${fields.name}...`,
      success: () => {
        router.push('/praias/salvas');
        return `${fields.name} criada com sucesso!`;
      },
      error: (error) => {
        const apiError = getApiError(error);

        if (apiError) {
          return apiError.message;
        } else {
          return 'Um erro inesperado aconteceu...';
        }
      },
    });
  }

  function onUpload(files: FileList) {
    if (files && files.length) {
      const file = files.item(0);

      form.setValue('image', file, { shouldValidate: true });
    }
  }

  useEffect(() => {
    const keys: (keyof Omit<FormFields, 'image' | 'position'>)[] = [
      'name',
      'city',
      'country',
      'countryCode',
      'lat',
      'lng',
    ];

    keys.forEach((key) => {
      if (data?.[key]) {
        const value =
          key === 'countryCode' ? data[key].toLocaleUpperCase() : data[key];
        form.setValue(key, value as string | number);
      }
    });
  }, [data, form]);

  return (
    <Dialog>
      {children}

      <DialogContent>
        <DialogHeader>
          <DialogTitle>Crie sua praia</DialogTitle>
          <DialogDescription>
            Já preenchemos algumas informações para você!
          </DialogDescription>
        </DialogHeader>

        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} noValidate>
            <ScrollArea>
              <div className='h-96 space-y-4 p-4'>
                <FormField
                  control={form.control}
                  name='name'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nome</FormLabel>
                      <FormControl>
                        <Input
                          type='text'
                          placeholder='Praia de Copacabana'
                          {...field}
                          disabled={isPending}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name='lat'
                  render={() => (
                    <FormItem>
                      <FormLabel>Latitude</FormLabel>
                      <FormControl>
                        <Input
                          type='number'
                          placeholder='-22.97570575'
                          {...form.register('lat', { valueAsNumber: true })}
                          disabled={isPending}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name='lng'
                  render={() => (
                    <FormItem>
                      <FormLabel>Longitude</FormLabel>
                      <FormControl>
                        <Input
                          type='number'
                          placeholder='-43.18662424789011'
                          {...form.register('lng', { valueAsNumber: true })}
                          disabled={isPending}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name='position'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Posição</FormLabel>
                      <FormControl>
                        <Select
                          onValueChange={field.onChange}
                          defaultValue={field.value}
                          disabled={isPending}
                        >
                          <SelectTrigger>
                            <SelectValue placeholder='Selecione a posição' />
                          </SelectTrigger>

                          <SelectContent>
                            <SelectGroup>
                              <SelectLabel>Posições</SelectLabel>
                              {Object.entries(Position).map(([key, value]) => (
                                <SelectItem key={value} value={value}>
                                  {key}
                                </SelectItem>
                              ))}
                            </SelectGroup>
                          </SelectContent>
                        </Select>
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name='country'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>País</FormLabel>
                      <FormControl>
                        <Input
                          type='text'
                          placeholder='Brasil'
                          {...field}
                          disabled={isPending}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name='city'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Cidade</FormLabel>
                      <FormControl>
                        <Input
                          type='text'
                          placeholder='Rio de Janeiro'
                          {...field}
                          disabled={isPending}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name='image'
                  render={({ fieldState: { error } }) => (
                    <DragAndDrop
                      acceptedTypes={ACCEPTED_IMAGE_TYPES}
                      fieldValue='image'
                      form={form}
                      maxSize={MAX_FILE_SIZE}
                      error={error}
                      onUpload={onUpload}
                    />
                  )}
                />
              </div>
            </ScrollArea>

            <DialogFooter className='mt-2'>
              <Button type='submit' disabled={isPending}>
                Criar
              </Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}
